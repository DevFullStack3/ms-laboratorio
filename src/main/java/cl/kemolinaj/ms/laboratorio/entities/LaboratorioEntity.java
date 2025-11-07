package cl.kemolinaj.ms.laboratorio.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "LABORATORIO", schema = "DFS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LaboratorioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "LABORATORIO_SEQ", sequenceName = "DFS.LABORATORIO_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODIGO")
    private String codigo;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "TIPO")
    private String tipo;
}
