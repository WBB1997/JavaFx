package Adapter;

import Charactor.Student;
import Util.InfoManager;
import Util.XmlUtil;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OracleAdapter implements InfoManager {
    private Connection connection = null;
    private static String TableName = XmlUtil.getTableName();

    @Override
    public String add(Student student) {
        String state = "添加成功！"; // 执行状态
        PreparedStatement ps = null;
        String sql = "INSERT INTO " + TableName + " VALUES(?,?,?,?,?,?,?)";
        try {
            connection = new DBConnection().getConnection();
            ps = connection.prepareStatement(sql);
            setAttribute(ps, student, 1);
            ps.executeUpdate();
            return state;
        } catch (SQLException s) {
            s.printStackTrace();
            state = s.getMessage();
        } finally {
            closeResource(connection, ps, null);
        }
        return state;
    }

    @Override
    public String delete(Student student) {
        PreparedStatement ps = null;
        String state = "删除成功！";
        String sql = "DELETE FROM " + TableName + " WHERE Sno = ? AND Cno = ?";
        try {
            connection = new DBConnection().getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, student.getSno());
            ps.setString(2, student.getCno());
            ps.executeUpdate();
            return state;
        } catch (SQLException s) {
            s.printStackTrace();
            state = s.getMessage();
        } finally {
            closeResource(connection, ps, null);
        }
        return state;
    }

    @Override
    public String update(Student oldone, Student newone) {
        PreparedStatement ps = null;
        String state = "更新成功！";
        String sql = "UPDATE " + TableName + " SET SNO=?,SNAME=?,SSEX=?,SAGE=?,SDEPT=?,CNO=?,GRADE=? " +
                "WHERE Sno = ? AND Cno = ?";
        try {
            connection = new DBConnection().getConnection();
            ps = connection.prepareStatement(sql);
            setAttribute(ps, newone, 1);
            ps.setString(8, oldone.getSno());
            ps.setString(9, oldone.getCno());
            ps.executeUpdate();
            return state;
        } catch (SQLException s) {
            s.printStackTrace();
            state = s.getMessage();
        } finally {
            closeResource(connection, ps, null);
        }
        return state;
    }

    @Override
    public List<Student> get(String args, String keyword) {
        setRowCount("SELECT COUNT(*) FROM " + TableName + " WHERE " + args + "='" + keyword + "'");
        String sql = getPageSql("SELECT * FROM " + TableName + " WHERE " + args + "='" + keyword + "'", PageControl.getPage());
        return getList(sql);
    }

    @Override
    public List<Student> get() {
        setRowCount("SELECT COUNT(*) FROM " + TableName);
        String sql = getPageSql("SELECT * FROM " + TableName, PageControl.getPage());
        return getList(sql);
    }

    @Override
    public List<String> getColumnNames() {
        Statement st = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM " + TableName;
        List<String> columnNames = new ArrayList<>();
        try {
            connection = new DBConnection().getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            for (int i = 1; i <= rsmd.getColumnCount(); i++)
                columnNames.add(rsmd.getColumnName(i));
        } catch (SQLException s) {
            s.printStackTrace();
            JOptionPane.showMessageDialog(null, s.getMessage(), "错误", JOptionPane.WARNING_MESSAGE);
        } finally {
            closeResource(connection, st, rs);
        }
        return columnNames;
    }

    private String getPageSql(String sql, int Page) {
        int pageSize = PageControl.getPageSize();
        int start = Page * pageSize + 1;
        int end = start + pageSize - 1;
        List<String> columnNames = getColumnNames();
        StringBuilder head = new StringBuilder();
        for (String str : columnNames)
            head.append(str).append(",");
        head.deleteCharAt(head.length() - 1);
        return "SELECT " + head + " FROM (SELECT tmp.*, ROWNUM rn FROM (" +
                sql +
                " ORDER BY SNO) tmp WHERE ROWNUM <= " +
                Integer.toString(end) +
                ") WHERE rn >= " +
                Integer.toString(start);
    }

    private void setRowCount(String sql) {
        Statement st;
        ResultSet rs;
        int count = 0;
        Connection connection;
        try {
            connection = new DBConnection().getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next())
                count = rs.getInt(1);
        } catch (SQLException s) {
            s.printStackTrace();
            JOptionPane.showMessageDialog(null, s.getMessage(), "错误", JOptionPane.WARNING_MESSAGE);
        }
        PageControl.setRowCount(count);
        System.out.println("RowCount：" + PageControl.getRowCount());
    }

    private void setAttribute(PreparedStatement ps, Student stu, int index) throws SQLException {
        ps.setString(index, stu.getSno());
        ps.setString(index + 1, stu.getSname());
        ps.setString(index + 2, stu.getSsex());
        ps.setInt(index + 3, Integer.parseInt(stu.getSage()));
        ps.setString(index + 4, stu.getSdept());
        ps.setString(index + 5, stu.getCno());
        ps.setInt(index + 6, Integer.parseInt(stu.getGrade()));
    }

    private void closeResource(Connection conn, Statement st, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private List<Student> getResult(ResultSet rs) throws SQLException {
        List<Student> res = new ArrayList<>();
        ResultSetMetaData rsmd = rs.getMetaData();
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= rsmd.getColumnCount(); i++)
            list.add(rsmd.getColumnName(i));
        while (rs.next()) {
            Student student = new Student();
            List<String> infolist = new ArrayList<>();
            for (int i = 1; i <= list.size(); i++) {
                if (rsmd.getColumnType(i) == Types.INTEGER)
                    infolist.add(Integer.toString(rs.getInt(i)));
                else
                    infolist.add(rs.getString(i));
            }
            student.setList(infolist);
            res.add(student);
        }
        return res;
    }

    private List<Student> getList(String sql) {
        Statement st = null;
        ResultSet rs = null;
        try {
            connection = new DBConnection().getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            return getResult(rs);
        } catch (SQLException s) {
            s.printStackTrace();
            JOptionPane.showMessageDialog(null, s.getMessage(), "错误", JOptionPane.WARNING_MESSAGE);
        } finally {
            closeResource(connection, st, rs);
        }
        return null;
    }
}
