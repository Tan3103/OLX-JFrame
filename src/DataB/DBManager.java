package DataB;
import Class.*;
import Menu.Login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DBManager {
    public static Connection connection = null;

    public void connect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/newnew?useUnicode=true&serverTimezone=UTC", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("" +
                    "INSERT INTO user (id, email, password, name, surname) " +
                    "VALUES (NULL, ?, ?, ?, ?)");
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getSurname());
            statement.executeUpdate();

            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public User getUser(String emaill){
        User user = new User();
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE email = '" + emaill + "'");
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                Integer id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                user = new User(id, email, password, name, surname);
            }
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    public ArrayList<Realty> getAllRealty(User user){
        ArrayList<Realty> List = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM item WHERE typeID = 1 AND UserID != '" +user.getId()+"'");
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                int numberRooms = resultSet.getInt("numberRooms");
                double square = resultSet.getInt("square");

                List.add(new Realty(id,  name, price, numberRooms, square));
            }
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return List;
    }

    public ArrayList<ClothingShoes> getAllCS(User user){
        ArrayList<ClothingShoes> List = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM item WHERE typeID = 2 AND UserID != '" + user.getId()+"'");
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                String numberRooms = resultSet.getString("size");

                List.add(new ClothingShoes(id,  name,  price, numberRooms));
            }
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return List;
    }

    public ArrayList<Animal> getAllAnimal(User user){
        ArrayList<Animal> List = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM item WHERE typeID = 3 AND UserID != '" +user.getId()+"'");
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                String breed = resultSet.getString("breed");
                int age = resultSet.getInt("age");

                List.add(new Animal(id,  name, price, breed, age));
            }
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return List;
    }

    public void deleteItem(Integer id){
        try{
            PreparedStatement statement = connection.prepareStatement("" + "DELETE FROM item WHERE id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addCart(User user, Item item) {
        try {
            PreparedStatement statement = connection.prepareStatement("" +
                    "INSERT INTO cart (id, userID, name, price) " +
                    "VALUES (NULL, ?, ?, ?)");
            statement.setInt(1,user.getId());
            statement.setString(2, item.getName());
            statement.setInt(3, item.getPrice());

            statement.executeUpdate();
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<Item> getAllCart(User user){
        ArrayList<Item> List = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("" +
                    "SELECT * FROM cart WHERE userID = '" + user.getId() + "'");

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                List.add(new Item(id, name, price));
            }
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return List;
    }

    public Item getItem(Integer idd){
        Item item = new Item();
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM item WHERE id = '" + idd + "'");
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                item = new Item(id, name, price);
            }

            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return item;
    }

    public void addRealty(Realty realty, User user) {
        try{
            PreparedStatement statement = connection.prepareStatement("" +
                    "INSERT INTO item (id, name, price, numberRooms, square, userID, typeID)" +
                    "VALUES (NULL, ?, ?, ?, ?, ?, ?)");

            statement.setString(1, realty.getName());
            statement.setInt(2, realty.getPrice());
            statement.setInt(3, realty.getNumberRooms());
            statement.setDouble(4, realty.getSquare());
            statement.setInt(5, user.getId());
            statement.setInt(6, 1);

            statement.executeUpdate();

            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addCS(ClothingShoes clothingShoes, User user) {
        try{
            PreparedStatement statement = connection.prepareStatement("" +
                    "INSERT INTO item (id, name, price, size, userID, typeID) " +
                    "VALUES (NULL, ?, ?, ?, ?, ?)");

            statement.setString(1, clothingShoes.getName());
            statement.setInt(2, clothingShoes.getPrice());
            statement.setString(3, clothingShoes.getSize());
            statement.setInt(4, user.getId());
            statement.setInt(5, 2);

            statement.executeUpdate();

            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addAnimal(Animal animal, User user) {
        try{
            PreparedStatement statement = connection.prepareStatement("" +
                    "INSERT INTO item (id, name, price, breed, age, userID, typeID) " +
                    "VALUES (NULL, ?, ?, ?, ?, ?, ?)");

            statement.setString(1, animal.getName());
            statement.setInt(2, animal.getPrice());
            statement.setString(3, animal.getBreed());
            statement.setInt(4, animal.getAge());
            statement.setInt(5, user.getId());
            statement.setInt(6, 3);

            statement.executeUpdate();

            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<Realty> getMyRealty(User user){
        ArrayList<Realty> List = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM item WHERE typeID = 1 AND userID = '" + user.getId() + "'");
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                int numberRooms = resultSet.getInt("numberRooms");
                double square = resultSet.getInt("square");

                List.add(new Realty(id,  name, price, numberRooms, square));
            }
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return List;
    }

    public ArrayList<ClothingShoes> getMyCS(User user){
        ArrayList<ClothingShoes> List = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM item WHERE typeID = 2 AND userID = '" + user.getId() + "'");
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                String numberRooms = resultSet.getString("size");

                List.add(new ClothingShoes(id,  name,  price, numberRooms));
            }
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return List;
    }

    public ArrayList<Animal> getMyAnimal(User user){
        ArrayList<Animal> List = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM item WHERE typeID = 3 AND userID = '" + user.getId() + "'");
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                String breed = resultSet.getString("breed");
                int age = resultSet.getInt("age");

                List.add(new Animal(id,  name, price, breed, age));
            }
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return List;
    }
}