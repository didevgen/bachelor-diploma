package ua.nure.providence.services.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Providence Team on 07.05.2017.
 */
public class XmlConverter<T> {

    public void convert(T object, Class<T> type) throws JAXBException, FileNotFoundException {
        JAXBContext contextObj = JAXBContext.newInstance(type);
        Marshaller marshallerObj = contextObj.createMarshaller();
        marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshallerObj.marshal(object, new FileOutputStream("iot\\ZKService\\ZKService\\configuration.xml"));
    }
}
