package psp.mviel.xarxajson;

/**
 * Created by mviel on 27/09/16.
 */
public class Contacte {
    int id;
    String name;
    String email;
    String address;
    String company_name;
    String companyCatchPhrase;

    public Contacte() {

    }

    public Contacte(int id, String name, String email, String address, String company_name, String companyCatchPhrase) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.company_name = company_name;
        this.companyCatchPhrase = companyCatchPhrase;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompanyCatchPhrase() {
        return companyCatchPhrase;
    }

    public void setCompanyCatchPhrase(String companyCatchPhrase) {
        this.companyCatchPhrase = companyCatchPhrase;
    }
    @Override
    public String toString(){
        return "id:"+this.id+"\tnom:"+this.name+"\tempresa:"+this.company_name+"\nFrase:"+this.companyCatchPhrase;
    }
}
