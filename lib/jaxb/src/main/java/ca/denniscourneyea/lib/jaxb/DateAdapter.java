package ca.denniscourneyea.lib.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

public class DateAdapter extends XmlAdapter<String, LocalDate> {

    @Override
    public LocalDate unmarshal(String s) throws Exception {
        return (s == null || s.isEmpty()) ? null : LocalDate.parse(s);
    }

    @Override
    public String marshal(LocalDate localDate) throws Exception {
        return localDate == null ? null : localDate.toString();
    }
}
