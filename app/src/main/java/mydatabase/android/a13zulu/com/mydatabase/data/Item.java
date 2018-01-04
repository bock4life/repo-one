package mydatabase.android.a13zulu.com.mydatabase.data;


import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

@Entity
public class Item {

    @Id
    long id;

    String itemName;
    String itemDescription;
    int itemQuantity;

    @Backlink
    public ToMany<ItemTransaction> mItemTransactions;

    public ToOne<StorageRoom> storageRoom;

    public Item(long id, String itemName, String itemDescription, int itemQuantity) {
        this.id = id;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemQuantity = itemQuantity;
    }

    public Item(String itemName, String itemDescription, int itemQuantity) {
        this.id = 0;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemQuantity = itemQuantity;
    }


    public Item() {

    }

    public boolean nameIsEmpty() {
        return (itemName == null || itemName.equals(""));
    }

    public boolean descriptionIsEmpty() {
        return (itemDescription == null || itemDescription.equals(""));
    }

    public boolean quantityIsIncorrect() {
        return (itemQuantity < 0);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public ToMany<ItemTransaction> getItemTransactions() {
        return mItemTransactions;
    }

    public void setItemTransactions(ToMany<ItemTransaction> itemTransactions) {
        mItemTransactions = itemTransactions;
    }

    public ToOne<StorageRoom> getStorageRoomId() {
        return storageRoom;
    }

    public void setStorageRoomId(ToOne<StorageRoom> storageRoom) {
        this.storageRoom = storageRoom;
    }

    public StorageRoom getItemStorageRoom(){
        return storageRoom.getTarget();
    }

    @Override
    public String toString() {
        return ("Item id: " + id +
                "\n Item name: " + itemName +
                "\n Item description: " + itemDescription +
                "\n Item quantity " + itemQuantity);
    }
}
