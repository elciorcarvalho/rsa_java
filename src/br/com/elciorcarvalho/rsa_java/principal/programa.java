package br.com.elciorcarvalho.rsa_java.principal;

import static br.com.elciorcarvalho.rsa_java.principal.RSAUtil.encrypt;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class programa {
    private static String publicKey = null;
    private static String privateKey = null;
    
    public static void main(String[] args) 
            throws NoSuchAlgorithmException, 
            IOException, 
            BadPaddingException, 
            IllegalBlockSizeException, 
            InvalidKeyException, 
            NoSuchPaddingException {
        
        
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String frase;
        String resp;
        String encryptedString;
        
        do{
            // Menu
            System.out.println("SELECIONE UMA OPÇÃO PELO NUMERO");
            System.out.println("===============================================");
            System.out.println("1 - Gerar chaves;");
            System.out.println("2 - Criptografar Mensagem;");
            System.out.println("3 - Descriptografar Mensagem;");
            System.out.println("4 - Sair;");
            System.out.println("================================================");
            System.out.print("Opção: ");
            resp = in.readLine();
            
            switch(resp){
                case "1":
                    RSAKeyPairGenerator keyPairGenerator = new RSAKeyPairGenerator();
                    keyPairGenerator.writeToFile("RSA/publicKey", keyPairGenerator.getPublicKey().getEncoded());
                    keyPairGenerator.writeToFile("RSA/privateKey", keyPairGenerator.getPrivateKey().getEncoded());
                    System.out.println("Chave Publica: ");
                    System.out.println(Base64.getEncoder().encodeToString(keyPairGenerator.getPublicKey().getEncoded()));
                    System.out.println("Chave Privada: ");
                    System.out.println(Base64.getEncoder().encodeToString(keyPairGenerator.getPrivateKey().getEncoded()));
                    break;
                    
                case "2":
                    try {
            
                        System.out.println("Chave Publica: ");
                        publicKey = in.readLine();
                        do{
                            System.out.println("Frase para criptografar [Maximo de 117 bytes]: ");
                            frase = in.readLine();
                        }while(frase.getBytes("utf8").length > 117);

                        encryptedString = Base64.getEncoder().encodeToString(encrypt(frase, publicKey));
                        System.out.println("Frase cifrada: ");
                        System.out.println(encryptedString);

                    } catch (NoSuchAlgorithmException e) {
                        System.err.println("Errou, malandro! Erro: " + e.getMessage());
                    } catch (IOException e) {
                        System.out.println("Erro de Entrada ou de saida de dados: " + e.getMessage());
                    }catch (InvalidKeyException e) {
                        System.out.println("Chave Ilegal: " + e.getMessage());
                    }
                    break;
                    
                case "3":
                    try {
            
                        System.out.println("Chave Privada: ");
                        privateKey = in.readLine();
                        
                        System.out.println("Cifra: ");
                        encryptedString = in.readLine();
                        String decryptedString = RSAUtil.decrypt(encryptedString, privateKey);
                        System.out.println("Frase original: ");
                        System.out.println(decryptedString);


                    } catch (NoSuchAlgorithmException e) {
                        System.err.println("Errou, malandro! Erro: " + e.getMessage());
                    } catch (IOException e) {
                        System.out.println("Erro de Entrada ou de saida de dados: " + e.getMessage());
                    }catch (InvalidKeyException e) {
                        System.out.println("Chave Ilegal: " + e.getMessage());
                    }
                    break;
                    
                case "4":
                    System.out.println("Obrigado por usar nosso RSA! Até logo.");
                    break;
                    
                default:
                    System.out.println("Opção inválida!");
            }
            System.out.println("\n\n");
            
        }while(!resp.equals("4"));
    }
}
