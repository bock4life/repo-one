package mydatabase.android.a13zulu.com.mydatabase.data;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

@Entity
public class StorageRoom {

    @Id
    long id;

    String name;
    String description;
    int backgroundColor;

    @Backlink
    public ToMany<Item> mItems;


    public StorageRoom(String name, String description, int backgroundColor) {
        this.id = 0;
        this.name = name;
        this.description = description;
        this.backgroundColor = backgroundColor;
    }

    public StorageRoom(long id, String name, String description, int backgroundColor) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.backgroundColor = backgroundColor;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public ToMany<Item> getItems() {
        return mItems;
    }

    public void setItems(ToMany<Item> items) {
        mItems = items;
    }
}
