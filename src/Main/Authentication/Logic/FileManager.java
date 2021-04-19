package Main.Authentication.Logic;

import Main.Authentication.Model.User;
import Main.Home.Model.Machine;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    public ArrayList<User>users=new ArrayList<User>();
    public ArrayList<Machine>machines=new ArrayList<Machine>();

    public void readSerializedFile(String path, String type) throws IOException {
        FileInputStream fis= null;
        ObjectInputStream ois=null;
        try {

            fis = new FileInputStream(this.getClass().getResource("/DB/"+path).getPath());
            ois= new ObjectInputStream(fis);

            Object o= ois.readObject();
            System.out.println(o);

            if (type.equals("users")) {
                ArrayList<User> temp=(ArrayList<User>) o;
                if(temp!=null)users=temp;

            } else {
                ArrayList<Machine> temp=(ArrayList<Machine>) o;
                if (temp!=null)machines=temp;
            }


        }
        catch(EOFException end) {

            //  ois.close();
            fis.close();

        } catch (ClassNotFoundException  | NullPointerException e) {
            e.printStackTrace();
        }

    }
    public void serializeToFile(String path, List<User> users) {
        try {

            FileOutputStream fos = new FileOutputStream(this.getClass().getResource("/DB/"+path).getPath());
            ObjectOutputStream oos=new ObjectOutputStream(fos);

            System.out.println("Writing..." + users);
            oos.writeObject(users);
            oos.reset();


            System.out.println("Completed!");
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void machineSerializeToFile(String path, ArrayList<Machine> machine) {
        try {

            FileOutputStream fos = new FileOutputStream(this.getClass().getResource("/DB/"+path).getPath());
            ObjectOutputStream oos=new ObjectOutputStream(fos);

            System.out.println("Writing..."+ machine);
            oos.writeObject(machine);
            oos.reset();


            System.out.println("Completed!");
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
