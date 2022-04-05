package test;

import static org.junit.Assert.*;

import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.runner.DataDrivenTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import suporte.Screenshot;
import suporte.Generator;
import suporte.Web;

import java.util.concurrent.TimeUnit;

@RunWith(DataDrivenTestRunner.class)
@DataLoader(filePaths = "InformacoesUsuarioTest.csv")

public class InformacoesUsuarioTest {
    private WebDriver navegador;

    @Rule
    public TestName test = new TestName();

    @Before
    public void setUp(){

        navegador = Web.createChrome();

        //Clicar no link que possui o texto "sign in"
        navegador.findElement(By.linkText("Sign in")).click();

        //Identificando o formulario de login
        WebElement formularioSignInBox = navegador.findElement(By.id("signinbox"));

        //Digitar no campo com name "login" que esta dentro do formulario de id "signinbox" o texto "golias161"
        formularioSignInBox.findElement(By.name("login")).sendKeys("golias161");

        //Digitar no campo com name "password" que esta dentro do formulario de id "signinbox" o texto "golias1601"
        formularioSignInBox.findElement(By.name("password")).sendKeys("golias161");

        //Clicar no link com o texto "SIGN IN"
        navegador.findElement(By.linkText("SIGN IN")).click();

        //clicar em um link que possui a class "me"

        navegador.findElement(By.className("me")).click();
        //clicar em um link que possui o texto "more data about you"
        navegador.findElement(By.linkText("MORE DATA ABOUT YOU")).click();
    }

    @Test
    public void testAdicionarUmaInformacaoAdicionalDoUsuario(@Param(name = "tipo")String tipo, @Param(name = "contato")String contato, @Param(name = "mensagem")String mensagemEsperada) {

        //clicar no botáo atraves do seu xpath //button[@data-target="addmoredata"]
        navegador.findElement(By.xpath("//button[@data-target=\"addmoredata\"]")).click();
        //identificar a pop-up onde esta o formulario de id "addmoredata"
        WebElement popupAddMoreData = navegador.findElement(By.id("addmoredata"));
        //na combo de name "type" escolher a opção "Phone"
        WebElement campoTyoe = popupAddMoreData.findElement(By.name("type"));
        new Select(campoTyoe).selectByVisibleText(tipo);
        //No campo de name "contact" digitar "13997969329"
        popupAddMoreData.findElement(By.name("contact")).sendKeys(contato);
        //Clicar No link de text "Save" que esta na pop-up
        popupAddMoreData.findElement(By.linkText("SAVE")).click();
        //Na mensagem de id "toats-container" validar que o texto é "Your contact has been added!"
        WebElement mensagemPop = navegador.findElement(By.id("toast-container"));
        String mensagem = mensagemPop.getText();
        assertEquals(mensagemEsperada, mensagem);

    }
    //@Test

    public void removerUmContatoDoUsuario(){

        //Clicar no elemento pelo seu xpath //span [text()="13997969329"]/following-sibling::a
        navegador.findElement(By.xpath("//span [text()=\"13997969329\"]/following-sibling::a")).click();

        //Confirmar a janela java script
        navegador.switchTo().alert().accept();

        //Validar que a mensagem apresentada foi "Reast in peace, dear phone!"
        WebElement mensagemPop = navegador.findElement(By.id("toast-container"));
        String mensagem = mensagemPop.getText();
        assertEquals("Rest in peace, dear phone!", mensagem);

        String screenshotArquivo = "C:\\Users\\lnv\\Desktop\\Screenshots\\" + Generator.dataHoraParaArquivo() + test.getMethodName() + ".png";
        Screenshot.tirar(navegador, screenshotArquivo);

        //Aguardar ate 10 segundos para que a janela desapareça
        WebDriverWait aguardar = new WebDriverWait(navegador, 10);
        aguardar.until(ExpectedConditions.stalenessOf(mensagemPop));

        //Clicar no link com o texto logout
        navegador.findElement(By.linkText("Logout")).click();
    }


    @After
    public void tearDown(){
        //Fechar o navegador
        navegador.quit();
    }
}
