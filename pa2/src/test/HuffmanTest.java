import core.HuffmanTree;
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
    hf.print();
//    log.info(hf.root.get().freq);
    var aCode = hf.code('a');
    var eCode = hf.code('e');
    var iCode = hf.code('i');
    var sCode = hf.code('s');
    var tCode = hf.code('t');
    var spCode = hf.code(' ');
    var nlCode = hf.code('\n');
    log.info(aCode);
    log.info(eCode);
    log.info(iCode);
    log.info(sCode);
    log.info(tCode);
    log.info(spCode);
    log.info(nlCode);
    Test.throwAssertQuiet("A == 001", aCode.isPresent() && aCode.get().equals("001"));
  }
}
