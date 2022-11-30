package br.com.babicakesbackend.models.entity;

import br.com.babicakesbackend.models.enumerators.EventOriginEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_event")
public class Event {
    @Id
    @Column(name = "id_event")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_device")
    private Device device;

    @Column(name = "event_title")
    private String title;

    @Column(name = "event_message")
    private String message;

    @Column(name = "event_image")
    private String image;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_status")
    private EventOriginEnum eventOriginEnum;

    @Column(name = "event_send")
    private boolean send;

    @Column(name = "event_visualized")
    private boolean visualized;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "event_date_send")
    private Date dateSend;

}
