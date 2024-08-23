package cloud.fabriciojunior.entity;

import cloud.fabriciojunior.entity.enums.TipoInstituicao;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "instituicoes")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Instituicao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID id;

    @Column(name = "nome", nullable = false, unique = true, length = 150)
    private String nome;

    @Column(name = "tipo", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoInstituicao tipo;

    @OneToMany(mappedBy = "instituicao", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<Evento> eventos = new ArrayList<>();

}
