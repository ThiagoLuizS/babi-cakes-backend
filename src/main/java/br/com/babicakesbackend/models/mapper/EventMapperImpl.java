package br.com.babicakesbackend.models.mapper;

import br.com.babicakesbackend.models.dto.EventForm;
import br.com.babicakesbackend.models.dto.EventView;
import br.com.babicakesbackend.models.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventMapperImpl implements MapStructMapper<Event, EventView, EventForm> {

    @Autowired
    private DeviceMapperImpl deviceMapper;

    @Override
    public EventView entityToView(Event event) {
        return EventView.builder()
                .id(event.getId())
                .device(deviceMapper.entityToView(event.getDevice()))
                .title(event.getTitle())
                .message(event.getMessage())
                .image(event.getImage())
                .eventOriginEnum(event.getEventOriginEnum().getProperty())
                .send(event.isSend())
                .visualized(event.isVisualized())
                .dateSend(event.getDateSend())
                .build();
    }

    @Override
    public Event formToEntity(EventForm eventForm) {
        return null;
    }

    @Override
    public EventForm viewToForm(EventView eventView) {
        return null;
    }

    @Override
    public EventForm entityToForm(Event event) {
        return null;
    }
}
