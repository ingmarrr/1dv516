import core.Problem7.HuffmanTree;
import logging.Logger;
import logging.Mode;
import testing.Test;
import testing.Unit;

public class HuffmanTest {

  private static Logger log = Logger.builder()
      .mode(Mode.Test)
      .emoji(true)
      .build();

  @Unit
  public void testGetCode() throws Test.FailException {
    var input = "\n             aaaaaaaaaaeeeeeeeeeeeeeeeiiiiiiiiiiiissstttt";
    var hf = new HuffmanTree(input);
//    hf.print();
    var aCode = hf.code('a');
    var eCode = hf.code('e');
    var iCode = hf.code('i');
    var sCode = hf.code('s');
    var tCode = hf.code('t');
    var spCode = hf.code(' ');
    var nlCode = hf.code('\n');
//    log.info("a", aCode);
//    log.info("e", eCode);
//    log.info("i", iCode);
//    log.info("s", sCode);
//    log.info("t", tCode);
//    log.info("sp", spCode);
//    log.info("\\n", nlCode);
    Test.throwAssertQuiet("A Len == 3", aCode.isPresent() && aCode.get().length() == 3);
    Test.throwAssertQuiet("E Len == 2", eCode.isPresent() && eCode.get().length() == 2);
    Test.throwAssertQuiet("I Len == 2", iCode.isPresent() && iCode.get().length() == 2);
    Test.throwAssertQuiet("S Len == 5", sCode.isPresent() && sCode.get().length() == 5);
    Test.throwAssertQuiet("T Len == 3", tCode.isPresent() && tCode.get().length() == 4);
    Test.throwAssertQuiet("SP Len == 3", spCode.isPresent() && spCode.get().length() == 2);
    Test.throwAssertQuiet("NL Len == 3", nlCode.isPresent() && nlCode.get().length() == 5);
  }
}
