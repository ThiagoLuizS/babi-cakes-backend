package br.com.babicakesbackend.models.dto;

import br.com.babicakesbackend.models.enumerators.EventOriginEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventView {
    private Long id;
    private DeviceView device;
    private String title;
    private String message;
    private String image;
    private PropertyStringDTO eventOriginEnum;
    private boolean send;
    private boolean visualized;
    private Date dateSend;
}
