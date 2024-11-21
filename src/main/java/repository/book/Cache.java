package repository.book;

//generic classes

import java.util.List;

public class Cache<T> {
    private List<T> storage;

    public List<T> load(){
        System.out.println("Loaded from cache");
        return storage;
    }

    public void save(List<T> storage){
        this.storage = storage;
    }

    public boolean hasResult(){
        return storage != null;
    }

    public void invalidateCache(){  //cand se scrie ceva in db vreau sa invalidez cache-ul
        storage = null;
    }
}
