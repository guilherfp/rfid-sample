package br.com.devsource.rfid.samples;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import br.com.devsource.rfid.all.SimpleAntenna;
import br.com.devsource.rfid.all.SimpleReaderConf;
import br.com.devsource.rfid.api.Antenna;
import br.com.devsource.rfid.api.ReadCommand;
import br.com.devsource.rfid.api.Reader;
import br.com.devsource.rfid.api.ReaderConf;
import br.com.devsource.rfid.api.ReadCommand.ReadCommandBuilder;
import br.com.devsource.rfid.api.tag.ReadTagField;
import br.com.devsource.rfid.bri.ReaderBri;

/**
 * @author Guilherme Pacheco
 */
public class SimpleReadBri {

  private static final String HOSTNAME = "192.168.0.100";

  public static void main(String[] args) throws InterruptedException {
    Reader reader = new ReaderBri(readerConf(args));
    reader.addHandler(System.out::println);
    reader.start(command());
    Thread.sleep(TimeUnit.SECONDS.toMillis(10));
    reader.disconnect();
  }

  private static ReadCommand command() {
    ReadCommandBuilder builder = ReadCommandBuilder.create();
    builder.add(ReadTagField.EPCID, ReadTagField.ANTENNA);
    return builder.build();
  }

  private static ReaderConf readerConf(String[] args) {
    SimpleReaderConf conf = new SimpleReaderConf();
    conf.setHostname(hostname(args[0]));
    conf.setPort(port(args[1]));
    conf.setAntennas(antennas());
    return conf;
  }

  private static String hostname(String host) {
    return notEmpty(host) ? host : HOSTNAME;
  }

  private static int port(String port) {
    return notEmpty(port) ? Integer.parseInt(port) : 0;
  }

  private static Set<Antenna> antennas() {
    Set<Antenna> antennas = new HashSet<>();
    SimpleAntenna antenna = new SimpleAntenna();
    antenna.setTransmitPower(100);
    antenna.setNumber(1);
    antennas.add(antenna);
    return antennas;
  }

  private static boolean notEmpty(String host) {
    return host != null && !host.isEmpty();
  }
}
