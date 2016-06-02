package b.com.macumbaria.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Macumba.
 */
@Entity
@Table(name = "macumba")
public class Macumba implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 2, max = 70)
    @Column(name = "destinatario", length = 70, nullable = false)
    private String destinatario;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestinatario() {
        return destinatario;
    }
    
    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Macumba macumba = (Macumba) o;
        if(macumba.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, macumba.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Macumba{" +
            "id=" + id +
            ", destinatario='" + destinatario + "'" +
            '}';
    }
}
