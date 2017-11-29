package mydatabase.android.a13zulu.com.mydatabase.data;

import java.util.Date;

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
    Date lastAccessed;

    @Backlink
    public ToMany<Item> mItems;


    public StorageRoom(String name, String description, Date lastAccessed) {
        this.id = 0;
        this.name = name;
        this.description = description;
        this.lastAccessed = lastAccessed;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLastAccessed() {
        return lastAccessed;
    }

    public void setLastAccessed(Date lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

    public ToMany<Item> getItems() {
        return mItems;
    }

    public void setItems(ToMany<Item> items) {
        mItems = items;
    }
}
