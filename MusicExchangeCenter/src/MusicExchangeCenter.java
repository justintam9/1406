import javafx.util.Pair;

import java.util.*;

public class MusicExchangeCenter {
    ArrayList<User> users;
    ArrayList <User> utemp = new ArrayList<User>();
    ArrayList <Song> atemp1 = new ArrayList<Song>();
    ArrayList <Song> atemp2 = new ArrayList<Song>();
    HashMap<String, Float> royalties = new HashMap<String,Float>();
    ArrayList <Song> downloadedSongs = new ArrayList<Song>();
    public MusicExchangeCenter(){
        users = new ArrayList<User>();
    }
    public ArrayList <User>onlineUsers(){
        utemp.clear();
        for (User i : users){
            if (i.isOnline()){
                utemp.add (i);
            }
        }
        return utemp;
    }
    public Song getSong (String title, String ownerName){
        User temp = new User();
        for (User i : users){
            if (i.isOnline()){
                if (i.getUserName() == ownerName) {
                    temp = i;
                }
            }
        }
        for (Song s : temp.songList){
            if (s.getTitle() == title){
                return s;
            }
        }
        return null;
    }
    public ArrayList<Song> getDownloadedSongs(){ return downloadedSongs;}
    public ArrayList<Song> allAvailableSongs(){
        atemp1.clear();
        for (User i : users){
            if (i.isOnline()){
                for (Song j : i.songList)
                atemp1.add(j);
            }
        }
        return atemp1;
    }
    public String toString(){
        return ("Music Exchange Center ("+onlineUsers().size()+" users on line, "+allAvailableSongs().size()+" songs available)");
    }
    public User userWithName (String s){
        for (User i : users){
            if (i.getUserName() == s){
                return i;
            }
        }
        return null;
    }
    public void registerUser (User x){
        if (userWithName(x.getUserName())==null){
            users.add (x);
        }
    }
    public ArrayList<Song> availableSongsByArtist (String artist){
        atemp2.clear();
        for (User i : users){
            if (i.isOnline()){
                for (Song j : i.songList)
                    if (j.getArtist() == artist){
                    atemp2.add(j);
                    }
            }
        }
        return atemp2;
    }
    public void displayRoyalties(){
        Float n1;
        for (Song i : uniqueDownloads()) {
            n1 = (float)0.0;
            for (Song j : downloadedSongs) {
                if (j.getTitle() == i.getTitle()) {
                    n1 += (float)0.25;
                }
            }
            if (royalties.containsKey(i.getArtist())){
                Float temp = royalties.get(i.getArtist());
                royalties.replace (i.getArtist(),temp+n1);
            }
            else {
                royalties.put (i.getArtist(),n1);
            }
        }
        System.out.println (String.format("%-7s %-8s","Amount","Artist"));
        System.out.println ("---------------");
        for (String i: royalties.keySet()){
            System.out.println (String.format("$%.2f %-8s",royalties.get(i),i));
        }
    }
    public TreeSet<Song> uniqueDownloads(){
        TreeSet<Song> temp = new TreeSet<Song>();
        for (Song i:downloadedSongs){
            temp.add(i);
        }

        return temp;
    }
    public ArrayList <Pair<Integer,Song>> songsByPopularity (){
        ArrayList <Pair<Integer,Song>> popular = new ArrayList <Pair<Integer,Song>>();
        int n1;
        Pair <Integer, Song> p;
        for (Song i : uniqueDownloads()){
            n1 = 0;
            String s = i.getTitle();
            for (Song j : downloadedSongs){
                if (j.getTitle()==s){
                    n1++;
                }
            }
            p=new Pair <> (n1,i);
            popular.add(p);
        }
        Collections.sort(popular,new Comparator<Pair<Integer, Song>>() {
            public int compare(Pair<Integer, Song> p1, Pair<Integer, Song> p2) {
                return p2.getKey().compareTo(p1.getKey());
            }
        });
        return popular;
    }
}
