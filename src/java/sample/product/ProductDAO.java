/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sample.utils.DBUtils;

/**
 *
 * @author ADMIN
 */
public class ProductDAO {
//===============================PRODUCT===================================
    //get prodcut to show list
    public List<ProductDTO> getAllProduct() throws SQLException {
        List<ProductDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
           conn = DBUtils.getConnection();
           if(conn!=null){
               String sql = "select productID, productName, images, price, quantity , categoryID from product";
               pstm = conn.prepareStatement(sql);
               rs = pstm.executeQuery();
               while (rs.next()) {                   
                   list.add(new ProductDTO(rs.getNString("productID"), 
                           rs.getNString("productName"), 
                           rs.getNString("images"), 
                           rs.getInt("price"), 
                           rs.getInt("quantity"),
                           rs.getInt("categoryID")));
               }
           }
        } catch (Exception e) {
        }finally{
            if(conn!= null) conn.close();
            if(pstm!= null) pstm.close();
            if(rs!=null) rs.close();
        }
        return list;    
    }
    
    //lay detail san pham bang id
    public ProductDTO getProByID(String categoryID) throws SQLException {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
           conn = DBUtils.getConnection();
           if(conn!=null){
               String sql = "select productID, productName, images, price, quantity, categoryID from product "
                       + "where productID = ?";
               pstm = conn.prepareStatement(sql);
               pstm.setString(1, categoryID);
               rs = pstm.executeQuery();
               while (rs.next()) {                   
                   return new ProductDTO(rs.getNString("productID"), 
                           rs.getNString("productName"), 
                           rs.getNString("images"), 
                           rs.getInt("price"), 
                           rs.getInt("quantity"),
                           rs.getInt("categoryID"));
               }
           }
        } catch (Exception e) {
        }finally{
            if(conn!= null) conn.close();
            if(pstm!= null) pstm.close();
            if(rs!=null) rs.close();
        }   
        return null;
    }
    
    //Search product by name
    public List<ProductDTO> searchProduct(String seachPro) throws SQLException {
        List<ProductDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
           conn = DBUtils.getConnection();
           if(conn!=null){
               String sql = "select productID, productName, images, price, quantity, categoryID from product "
                       + "where productName like ?";
               pstm = conn.prepareStatement(sql);
               pstm.setString(1, "%" +seachPro+ "%" );
               rs = pstm.executeQuery();
               while (rs.next()) {                   
                String productID = rs.getString("productID");
                    String productName = rs.getString("productName");
                    String images = rs.getString("images");
                    int price = rs.getInt("price");
                    int quantity = rs.getInt("quantity");
                    int categoryID = rs.getInt("categoryID");
                    list.add(new ProductDTO(productID, productName, images, price, quantity, categoryID));
               }
           }
        } catch (Exception e) {
        }finally{
            if(conn!= null) conn.close();
            if(pstm!= null) pstm.close();
            if(rs!=null) rs.close();
        }   
        return list;
    }
    //delete item
    public boolean delete(String pid) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet resultSet = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "delete from product where productID = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, pid);
                check = pstm.executeUpdate() > 0 ? true : false;
            }
        } catch (Exception event) {

        } finally {
            if (pstm != null) {
                pstm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }
    
    //UPDATE ITEM
    public boolean Update(ProductDTO pro) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "UPDATE product SET productName =?, images =?, price = ?, quantity=?, categoryID= ?"
                        + " WHERE productID =?";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, pro.getProductName());
                pstm.setString(2, pro.getImage());
                pstm.setFloat(3, pro.getPrice());
                pstm.setInt(4, pro.getQuantity());
                pstm.setInt(5, pro.getCategoryID());
                pstm.setString(6, pro.getProductID());
                check = pstm.executeUpdate() > 0 ? true : false;
            }
        } catch (Exception event) {
            event.printStackTrace();
        } finally {
            if (pstm != null) {
                pstm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }
    
    //check trung id san pham
    public ProductDTO getProInfor(String productID) throws SQLException {
        ProductDTO product = null;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet resultSet = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "select productID, productName, images, price, quantity, categoryID from product "
                        + "WHERE productID= ?";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, productID);
                resultSet = pstm.executeQuery();
                if (resultSet.next()) {
                    String productName = resultSet.getString("productName");
                    String image = resultSet.getString("images");
                    float price = resultSet.getFloat("price");
                    int quantity = resultSet.getInt("quantity");
                    int categoryID = resultSet.getInt("categoryID");
                    product = new ProductDTO(productID, productName, image, price, quantity, categoryID);
                }
            }
        } catch (Exception event) {
            event.printStackTrace();
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (pstm != null) {
                pstm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return product;
    }
    
    
//============================CATEGORY=========================    
    public List<CategoryDTO> getCategory() throws SQLException{
        List<CategoryDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
           conn = DBUtils.getConnection();
           if(conn!=null){
               String sql = "select categoryID, CateName from category";
               pstm = conn.prepareStatement(sql);
               rs = pstm.executeQuery();
               while (rs.next()) {                   
                   list.add(new CategoryDTO(rs.getInt("categoryID"),
                           rs.getNString("CateName")));
               }
           }
        } catch (Exception e) {
        }finally{
            if(conn!= null) conn.close();
            if(pstm!= null) pstm.close();
            if(rs!=null) rs.close();
        }
        return list;    
    } 
    
   //lam category
    public List<ProductDTO> getProByCID(String categoryID) throws SQLException {
        //List<ProductDTO> list = null;
        List<ProductDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
           conn = DBUtils.getConnection();
           if(conn!=null){
               //list = new ArrayList<>();
               String sql = "select productID, productName, images, price, quantity , categoryID from product "
                       + "where categoryID = ?";
               pstm = conn.prepareStatement(sql);
               pstm.setString(1, categoryID);
               rs = pstm.executeQuery();
               while (rs.next()) {                   
                   list.add(new ProductDTO(rs.getNString("productID"), 
                           rs.getNString("productName"), 
                           rs.getNString("images"), 
                           rs.getInt("price"), 
                           rs.getInt("quantity"),
                           rs.getInt("categoryID")));
               }
           }
        } catch (Exception e) {
        }finally{
            if(conn!= null) conn.close();
            if(pstm!= null) pstm.close();
            if(rs!=null) rs.close();
        }
        return list;    
    }
    
    public boolean insertProduct(ProductDTO pro) throws SQLException{
        boolean check = false;
        Connection conn=null;
        PreparedStatement stm=null;
        try {
            conn=DBUtils.getConnection();
            if(conn!=null){
                String sql= " INSERT INTO product(productID, productName, images, price, quantity, categoryID) "
                        + " VALUES(?,?,?,?,?,?) ";
                stm=conn.prepareStatement(sql);
                stm.setString(1, pro.getProductID());
                stm.setString(2, pro.getProductName());
                stm.setString(3, pro.getImage());
                stm.setFloat(4, pro.getPrice());
                stm.setInt(5, pro.getQuantity());
                stm.setInt(6, pro.getCategoryID());
                check=stm.executeUpdate()> 0 ? true : false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(stm!=null) stm.close();
            if(conn!=null) conn.close();
        }
        return check;
    }
    
}
