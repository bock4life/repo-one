package mydatabase.android.a13zulu.com.mydatabase.data;


import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;


@Entity
public class ItemTransaction {

    @Id
    long id;

    long itemId;
    String itemName;
    int quantity;
    Date transactionDate;

    public ToOne<Item> item;

    public ItemTransaction(String itemName, int quantity, Date transactionDate) {
        this.id = 0;
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.transactionDate = transactionDate;
    }

    public ItemTransaction(long id,long itemId, String itemName, int quantity, Date transactionDate) {
        this.id = id;
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.transactionDate = transactionDate;
    }

    public ItemTransaction(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return ("ItemTransaction id: " + id +
                //"\nItem id: " + itemId +
                "\nItem name: " + itemName +
                "\nQuantity: " + quantity +
                "\nDate: " + transactionDate.toString());
    }
}
