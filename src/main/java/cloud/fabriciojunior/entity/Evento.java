package cloud.fabriciojunior.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "eventos")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID id;

    @Column(name = "nome", nullable = false, unique = true, length = 150)
    private String nome;

    @Column(name = "dh_inicio", nullable = false)
    private LocalDateTime dataHoraInicio;

    @Column(name = "dh_fim")
    private LocalDateTime dataHoraFim;

    @Column
    @Builder.Default
    private boolean ativo = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "i_instituicoes", nullable = false)
    private Instituicao instituicao;

    public void encerraSeNecessario() {
        if (this.dataHoraFim.isBefore(LocalDateTime.now())) {
            this.ativo = false;
        }
    }
}
