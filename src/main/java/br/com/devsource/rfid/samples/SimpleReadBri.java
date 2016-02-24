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
    Reader reader = new ReaderBri(readerConf());
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

  private static ReaderConf readerConf() {
    SimpleReaderConf conf = new SimpleReaderConf();
    conf.setHostname(HOSTNAME);
    conf.setAntennas(antennas());
    return conf;
  }

  private static Set<Antenna> antennas() {
    Set<Antenna> antennas = new HashSet<>();
    SimpleAntenna antenna = new SimpleAntenna();
    antenna.setTransmitPower(100);
    antenna.setNumber(1);
    antennas.add(antenna);
    return antennas;
  }

}
