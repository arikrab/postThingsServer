package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;


public class ClientThread extends Thread {

    private final int newPost=300;
    private final int getAllPosts=301;
    private final int getSpecificPost=302;
    private final int getAllUserList=304;


    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;


    public ClientThread(Socket socket){
        this.socket=socket;

    }

    @Override
    public void run() {
        try {
            //test purpse
            userPool.userPool.put("arik", -258530387);
            //
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            User user = new User();
            char option;
            int actualyread;
            byte[] bytebuffer = new byte[4];
            byte[] stringBuffer = new byte[1];

            //getting from user what he want to do
            actualyread = inputStream.read(stringBuffer);
            boolean exitLoop = false;
            int flag = 0;
            while (exitLoop == false) {
                option = (char) stringBuffer[0];
                if (flag > 0) {
                    actualyread = inputStream.read(stringBuffer);
                    option = (char) stringBuffer[0];
                }

                //***************************************
                //if login option
                if (option == '1') {
                    //get string user name  length
                    System.out.println("user choose login");
                    actualyread = inputStream.read(bytebuffer);
                    if (actualyread != 4) {
                        System.out.println("something went wrong, disconnecting...");
                    }
                    //getting the actual string
                    stringBuffer = new byte[ByteBuffer.wrap(bytebuffer).getInt()];
                    actualyread = inputStream.read(stringBuffer);
                    user.setUserName(new String(stringBuffer, "UTF-8"));
                    //get hashcode
                    actualyread = inputStream.read(bytebuffer);
                    if (actualyread != 4) {
                        System.out.println("something went wrong closing connection to socket");
                        inputStream.close();
                        outputStream.close();
                    }
                    user.setHashCode(ByteBuffer.wrap(bytebuffer).getInt());
                    //send activation token status
                    if (userPool.userPool.containsKey(user.getUserName()) == true) {
                        bytebuffer = new byte[4];
                        ByteBuffer.wrap(bytebuffer).putInt(1);
                        outputStream.write(bytebuffer);
                        exitLoop = true;
                    } else {
                        bytebuffer = new byte[4];
                        ByteBuffer.wrap(bytebuffer).putInt(0);
                        outputStream.write(bytebuffer);
                    }
                } else if (option == '2') {
                    //////////////////////////////////////////////////////////
                    ///////////////////can be put in a function///////////////
                    //get string user name  length
                    System.out.println("user choose singup");
                    actualyread = inputStream.read(bytebuffer);
                    if (actualyread != 4) {
                        System.out.println("something went wrong, disconnecting...");
                    }
                    //getting the actual string
                    stringBuffer = new byte[ByteBuffer.wrap(bytebuffer).getInt()];
                    actualyread = inputStream.read(stringBuffer);
                    user.setUserName(new String(stringBuffer, "UTF-8"));
                    //get hashcode
                    actualyread = inputStream.read(bytebuffer);
                    if (actualyread != 4) {
                        System.out.println("something went wrong closing connection to socket");
                        inputStream.close();
                        outputStream.close();
                    }
                    user.setHashCode(ByteBuffer.wrap(bytebuffer).getInt());
                    ////////////////////////////////////////////////////////////////////////////
                    if (userPool.userPool.containsKey(user.getUserName())) {

                        ByteBuffer.wrap(bytebuffer).putInt(0);
                        outputStream.write(bytebuffer);
                    } else {

                        ByteBuffer.wrap(bytebuffer).putInt(1);
                        outputStream.write(bytebuffer);
                        addUser(user);
                        ItemPool.itemPool.put(user.getUserName(), new ArrayList<Item>());
                        exitLoop = true;
                    }

                }
                flag++;
            }



            while (exitLoop = true) {
                int useraction;
                actualyread = inputStream.read(bytebuffer);
                if (actualyread != 4) {
                    System.out.println("something went wrong");
                    return;
                }
                    useraction = ByteBuffer.wrap(bytebuffer).getInt();
                    //private final int newPost=300;
                    //private final int getAllPosts=301;
                    //private final int getSpecificPost=302;
                    //private final int getAllUserList=304;

                    switch (useraction) {
                        case newPost: {
                            System.out.println("user choose post");
                            //in - username length
                            inputStream.read(bytebuffer);
                            if (actualyread != 4) {
                                System.out.println("something went wrong");
                                return;
                            }
                            //in - username
                            stringBuffer = new byte[ByteBuffer.wrap(bytebuffer).getInt()];
                            actualyread = inputStream.read(stringBuffer);
                            if (actualyread != stringBuffer.length) {
                                System.out.println("something went wrong");
                            }
                            //in - item name length
                            actualyread = inputStream.read(bytebuffer);
                            if (actualyread != 4) {
                                System.out.println("something went wrong");
                                return;
                            }
                            //in - item name
                            stringBuffer = new byte[ByteBuffer.wrap(bytebuffer).getInt()];
                            actualyread = inputStream.read(stringBuffer);
                            if (actualyread != stringBuffer.length) {
                                System.out.println("something went wrong");
                            }
                            //in - item phone
                            actualyread = inputStream.read(bytebuffer);
                            if (actualyread != 4) {
                                System.out.println("something went wrong");
                                return;
                            }
                            //in - item price
                            actualyread = inputStream.read(bytebuffer);
                            if (actualyread != 4) {
                                System.out.println("something went wrong");
                                return;
                            }
                            //out - success
                            ByteBuffer.wrap(bytebuffer).putInt(1);
                            outputStream.write(bytebuffer);
                        }
                        case getAllPosts: {
                            //send number of items in the list
                            ByteBuffer.wrap(bytebuffer).putInt(ItemPool.itemCount);
                            outputStream.write(bytebuffer);
                            //
                            for (int i=0;i<ItemPool.itemCount;i++){
                                Item item=new Item(ItemPool.unorderdItemList.get(i));
                                //send number of the letters of the string
                                ByteBuffer.wrap(bytebuffer).putInt(item.getItemName().length());
                                outputStream.write(bytebuffer);
                                //send actual item name
                                ByteBuffer.wrap(stringBuffer).put(item.getItemName().getBytes());
                                outputStream.write(stringBuffer);
                                //send phone number
                                ByteBuffer.wrap(bytebuffer).putInt(item.getPhone());
                                outputStream.write(bytebuffer);
                                //send price
                                ByteBuffer.wrap(bytebuffer).putInt(item.getPrice());
                                outputStream.write(bytebuffer);

                            }
                        }
                        case getSpecificPost: {

                        }
                        case getAllUserList: {

                        }
                    }


                }

            } catch(IOException e){
                e.printStackTrace();
            }

            /////////////////


        }




    public synchronized  void addUser(User user){
        userPool.userPool.put(user.getUserName(),user.getHashCode());
    }
    public synchronized void addItem(User user,Item item){
        UnsortedItem unsortedItem=new UnsortedItem(item,user.getUserName());
        ArrayList<Item> tempItemList;
        tempItemList=ItemPool.itemPool.get(user.getUserName());
        tempItemList.add(item);
        ItemPool.itemPool.put(user.getUserName(),tempItemList);
        ItemPool.unorderdItemList.add(unsortedItem);
    }
}


