package Charactor;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private SimpleStringProperty Sno;
    private SimpleStringProperty Sname;
    private SimpleStringProperty Ssex;
    private SimpleStringProperty Sage;
    private SimpleStringProperty Sdept;
    private SimpleStringProperty Cno;
    private SimpleStringProperty Grade;

    private List<String> InfoList = new ArrayList<>();

    public Student() {
        Sno = new SimpleStringProperty();
        Sname = new SimpleStringProperty();
        Ssex = new SimpleStringProperty();
        Sage = new SimpleStringProperty();
        Sdept = new SimpleStringProperty();
        Cno = new SimpleStringProperty();
        Grade = new SimpleStringProperty();
    }

    public String getCno() {
        return Cno.get();
    }

    public String getGrade() {
        return Grade.get();
    }

    public String getSage() {
        return Sage.get();
    }

    public String getSdept() {
        return Sdept.get();
    }

    public String getSname() {
        return Sname.get();
    }

    public String getSno() {
        return Sno.get();
    }

    public String getSsex() {
        return Ssex.get();
    }

    public void setCno(String cno) {
        if(Cno == null)
            Cno = new SimpleStringProperty(cno);
        else
            Cno.set(cno);
    }

    public void setSage(String sage) {
            Sage.set(sage);
    }

    public void setGrade(String grade) {
            Grade.set(grade);
    }

    public void setSdept(String sdept) {
            Sdept.set(sdept);
    }

    public void setSname(String sname) {
            Sname.set(sname);
    }

    public void setSno(String sno) {
            Sno.set(sno);
    }

    public void setSsex(String ssex) {
        if(Ssex == null)
            Ssex = new SimpleStringProperty(ssex);
        else
            Ssex.set(ssex);
    }

    public void setList(List<String> list){
        InfoList = list;
        for(int i = 0; i < InfoList.size(); i++){
            switch (i){
                case 0: Sno.setValue(InfoList.get(i));break;
                case 1: Sname.setValue(InfoList.get(i));break;
                case 2: Ssex.setValue(InfoList.get(i));break;
                case 3: Sage.setValue(InfoList.get(i));break;
                case 4: Sdept.setValue(InfoList.get(i));break;
                case 5: Cno.setValue(InfoList.get(i));break;
                case 6: Grade.setValue(InfoList.get(i));break;
            }
        }
    }

    public List<String> getList(){
        return InfoList;
    }

    public int getAttrCounts(){
        return 7;
    }

    @Override
    public String toString() {
        return Sno.get() + "," + Sname.get() + "," + Ssex.get() + "," + Sage.get() + "," + Sdept.get() + "," + Cno.get() + "," + Grade.get();
    }
}
