package hiberspring.util;

import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;

@Component
public class XmlParserImpl implements XmlParser{
    @Override
    public <T> T parseXml(Class<T> objectClass, String filePath) throws JAXBException, FileNotFoundException {
        final JAXBContext context = JAXBContext.newInstance(objectClass);
        final Unmarshaller unmarshaller = context.createUnmarshaller();



        return (T) unmarshaller.unmarshal(new FileReader(filePath));


    }
}
