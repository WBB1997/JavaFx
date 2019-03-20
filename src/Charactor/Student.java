package Charactor;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class Student {
    private String Sno;
    private String Sname;
    private String SSex;
    private Integer Sage;
    private String Sdept;
    private Integer SAvgGrade;
    private Blob SPicture;

    private List<Object> InfoList = new ArrayList<>();

    public Student() {
    }

    public String getSno() {
        return Sno;
    }

    public String getSname() {
        return Sname;
    }

    public String getSSex() {
        return SSex;
    }

    public Integer getSage() {
        return Sage;
    }

    public String getSdept() {
        return Sdept;
    }

    public Integer getSAvgGrade() {
        return SAvgGrade;
    }

    public Blob getSPicture() {
        return SPicture;
    }

    public List<Object> getInfoList() {
        return InfoList;
    }

    public void setSno(String sno) {
        Sno = sno;
    }

    public void setSname(String sname) {
        Sname = sname;
    }

    public void setSSex(String SSex) {
        this.SSex = SSex;
    }

    public void setSage(Integer sage) {
        Sage = sage;
    }

    public void setSdept(String sdept) {
        Sdept = sdept;
    }

    public void setSAvgGrade(Integer SAvgGrade) {
        this.SAvgGrade = SAvgGrade;
    }

    public void setSPicture(Blob SPicture) {
        this.SPicture = SPicture;
    }

    public void setInfoList(List<Object> infoList) {
        InfoList = infoList;
    }

    public void setList(List<Object> list){
        InfoList = list;
        for(int i = 0; i < InfoList.size(); i++){
            switch (i){
                case 0: Sno = (String) InfoList.get(i);break;
                case 1: Sname = (String) InfoList.get(i);break;
                case 2: SSex = (String) InfoList.get(i);break;
                case 3: Sage = (Integer) InfoList.get(i);break;
                case 4: Sdept = (String) InfoList.get(i);break;
                case 5: SAvgGrade = (Integer) InfoList.get(i);break;
                case 6: SPicture = (Blob) InfoList.get(i);break;
            }
        }
    }

    public List<Object> getList(){
        return InfoList;
    }

    @Override
    public String toString() {
        return Sno + "," + Sname + "," + SSex + "," + Sage + "," + Sdept + "," + SAvgGrade + "," + SPicture;
    }
}
