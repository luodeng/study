package com.roden.study.examples.org.yaml.snakeyaml;

import org.junit.Test;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * https://github.com/eugenp/tutorials/blob/master/libraries-data-io/src/test/java/com/baeldung/libraries/snakeyaml/YAMLToJavaDeserialisationUnitTest.java
 */
public class YamlTest {

    @Test
    public void base(){
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass().getResourceAsStream("customer.yaml");
        Customer customer = yaml.loadAs(inputStream,Customer.class);
        System.out.println(customer);
    }

    @Test
    public void  whenLoadYAMLDocument_thenLoadCorrectMap(){
        Yaml yaml = new Yaml();
        InputStream inputStream = YamlTest.class.getResourceAsStream("customer.yaml");
        Map<String, Object> obj = yaml.load(inputStream);
        assertEquals("John", obj.get("firstName"));
        assertEquals("Doe", obj.get("lastName"));
        assertEquals(20, obj.get("age"));
    }

    @Test
    public void whenLoadYAMLDocumentWithTopLevelClass_thenLoadCorrectJavaObject(){
        Yaml yaml = new Yaml(new Constructor(Customer.class));
        InputStream inputStream = this.getClass().getResourceAsStream("customer.yaml");
        Customer customer = yaml.load(inputStream);
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
        assertEquals(20, customer.getAge());
    }

    @Test
    public void whenLoadYAMLDocumentWithAssumedClass_thenLoadCorrectJavaObject() {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass().getResourceAsStream("customer_with_type.yaml");
        Customer customer = yaml.load(inputStream);
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
        assertEquals(20, customer.getAge());
    }

    @Test
    public void whenLoadYAML_thenLoadCorrectImplicitTypes() {
        Yaml yaml = new Yaml();
        Map<Object, Object> document = yaml.load("3.0: 2018-07-22");

        assertNotNull(document);
        assertEquals(1, document.size());
        assertTrue(document.containsKey(3.0d));
    }

    @Test
    public void whenLoadYAMLDocumentWithTopLevelClass_thenLoadCorrectJavaObjectWithNestedObjects() {
        Yaml yaml = new Yaml(new Constructor(Customer.class));
        InputStream inputStream = this.getClass().getResourceAsStream("customer_with_contact_details_and_address.yaml");
        Customer customer = yaml.load(inputStream);
        assertNotNull(customer);
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
        assertEquals(31, customer.getAge());
        assertNotNull(customer.getContactDetails());
        assertEquals(2, customer.getContactDetails().size());
        assertEquals("mobile", customer.getContactDetails().get(0).getType());
        assertEquals(123456789,customer.getContactDetails().get(0).getNumber());
        assertEquals("landline", customer.getContactDetails().get(1).getType());
        assertEquals(456786868, customer.getContactDetails().get(1).getNumber());
        assertNotNull(customer.getHomeAddress());
        assertEquals("Xyz, DEF Street", customer.getHomeAddress().getLine());
    }

    @Test
    public void whenLoadYAMLDocumentWithTypeDescription_thenLoadCorrectJavaObjectWithCorrectGenericType() {
        Constructor constructor = new Constructor(Customer.class);
        TypeDescription customTypeDescription = new TypeDescription(Customer.class);
        customTypeDescription.addPropertyParameters("contactDetails", Contact.class);
        constructor.addTypeDescription(customTypeDescription);
        Yaml yaml = new Yaml(constructor);
        InputStream inputStream = this.getClass().getResourceAsStream("customer_with_contact_details.yaml");
        Customer customer = yaml.load(inputStream);
        assertNotNull(customer);
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
        assertEquals(31, customer.getAge());
        assertNotNull(customer.getContactDetails());
        assertEquals(2, customer.getContactDetails().size());
        assertEquals("mobile", customer.getContactDetails().get(0).getType());
        assertEquals("landline", customer.getContactDetails().get(1).getType());
    }

    @Test
    public void whenLoadMultipleYAMLDocuments_thenLoadCorrectJavaObjects() {
        Yaml yaml = new Yaml(new Constructor(Customer.class));
        InputStream inputStream = this.getClass().getResourceAsStream("customers.yaml");
        int count = 0;
        for (Object object : yaml.loadAll(inputStream)) {
            count++;
            assertTrue(object instanceof Customer);
        }
        assertEquals(2, count);
    }


    @Test
    public void whenDumpMap_thenGenerateCorrectYAML() {
        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("name", "Silenthand Olleander");
        data.put("race", "Human");
        data.put("traits", new String[] { "ONE_HAND", "ONE_EYE" });
        Yaml yaml = new Yaml();
        StringWriter writer = new StringWriter();
        yaml.dump(data, writer);
        String expectedYaml = "name: Silenthand Olleander\nrace: Human\ntraits: [ONE_HAND, ONE_EYE]\n";
        assertEquals(expectedYaml, writer.toString());
    }

    @Test
    public void whenDumpACustomType_thenGenerateCorrectYAML() {
        Customer customer = new Customer();
        customer.setAge(45);
        customer.setFirstName("Greg");
        customer.setLastName("McDowell");
        Yaml yaml = new Yaml();
        StringWriter writer = new StringWriter();
        yaml.dump(customer, writer);
        System.out.println(writer.toString());
    }

    @Test
    public void whenDumpAsCustomType_thenGenerateCorrectYAML() {
        Customer customer = new Customer();
        customer.setAge(45);
        customer.setFirstName("Greg");
        customer.setLastName("McDowell");
        Yaml yaml = new Yaml();
        String expectedYaml = yaml.dumpAs(customer, Tag.MAP, null);
        System.out.println(expectedYaml);
    }

}
