package com.company;

public class UnsortedItem extends Item {
    private String userName;
    public UnsortedItem(Item item, String userName) {
        super(item);
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof UnsortedItem)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        UnsortedItem c = (UnsortedItem) o;


        if (c.getUserName()!=((UnsortedItem) o).getUserName()||c.getItemName()!=((UnsortedItem) o).getItemName()
        ||c.getPhone()!=((UnsortedItem) o).getPhone()||c.getPrice()!=c.getPrice()){
            return false;
        }
        else
            return true;
    }
}

