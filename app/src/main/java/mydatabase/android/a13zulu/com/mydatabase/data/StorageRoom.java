package mydatabase.android.a13zulu.com.mydatabase.data;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class StorageRoom {

    @Id
    long id;

    String name;
//    @Backlink
//    public ToMany<Item> mItems;


    public StorageRoom(String name) {
        this.id = 0;
        this.name = name;
    }

    public StorageRoom(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public StorageRoom(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public List<Item> getItems() {
//        return items;
//    }
//
//    public void setItems(List<Item> items) {
//        this.items = items;
//    }
}
