package DB;

import javax.crypto.Cipher;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.Key;
import java.security.Security;

public class DESPlus {

	private static String strDefaultKey = "GHS_Colin_Midas_Chun0";

	private static Cipher encryptCipher = null;

	private static Cipher decryptCipher = null;

	/**
	 * 指定密钥构造方法
	 * 
	 * @param strKey
	 *            指定的密钥
	 * @throws Exception
	 */
	static {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Key key;
		try {
			key = getKey(strDefaultKey.getBytes());
			encryptCipher = Cipher.getInstance("DES");
			encryptCipher.init(Cipher.ENCRYPT_MODE, key);
			decryptCipher = Cipher.getInstance("DES");
			decryptCipher.init(Cipher.DECRYPT_MODE, key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String pswd;
	private static String password;

	public static String rePassword(String p) {
		pswd = p;
		try {
			password = decrypt(pswd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return password;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String p) {
		password = p;
	}

	public static String getPswd() {
		return pswd;
	}

	public static void setPswd(String p) {
		pswd = p;
		try {
			password = decrypt(pswd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
	 * hexStr2ByteArr(String strIn) 互为可逆的转换过程
	 * 
	 * @param arrB
	 *            需要转换的byte数组
	 * @return 转换后的字符串
	 * @throws Exception
	 *             本方法不处理任何异常，所有异常全部抛出
	 */
	public static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍..一个byte转成16进制最多不会超过两位。FF
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16)); // 16代表进制
		}
		return sb.toString();
	}

	/**
	 * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
	 * 互为可逆的转换过程
	 * 
	 * @param strIn
	 *            需要转换的字符串
	 * @return 转换后的byte数组
	 * @throws Exception
	 *             本方法不处理任何异常，所有异常全部抛出
	 * @author <a href="mailto:leo841001@163.com">LiGuoQing</a>
	 */
	public static byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	/**
	 * 加密字节数组
	 * 
	 * @param arrB
	 *            需加密的字节数组
	 * @return 加密后的字节数组
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] arrB) throws Exception {
		return encryptCipher.doFinal(arrB);
	}

	/**
	 * 加密字符串
	 * 
	 * @param strIn
	 *            需加密的字符串
	 * @return 加密后的字符串
	 * @throws Exception
	 */
	public static String encrypt(String strIn) {
		try {
			if (null == strIn) {
				return strIn;
			}
			return byteArr2HexStr(encrypt(strIn.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(strIn.getBytes());

	}

	/**
	 * 解密字节数组
	 * 
	 * @param arrB
	 *            需解密的字节数组
	 * @return 解密后的字节数组
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] arrB) throws Exception {
		return decryptCipher.doFinal(arrB);
	}

	/**
	 * 解密字符串
	 * 
	 * @param strIn
	 *            需解密的字符串
	 * @return 解密后的字符串
	 * @throws Exception
	 */
	public static String decrypt(String strIn) throws Exception {
		if (null == strIn) {
			return strIn;
		}
		return new String(decrypt(hexStr2ByteArr(strIn)));
	}

	/**
	 * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
	 * 
	 * @param arrBTmp
	 *            构成该字符串的字节数组
	 * @return 生成的密钥
	 * @throws Exception
	 */
	private static Key getKey(byte[] arrBTmp) throws Exception {
		// 创建一个空的8位字节数组（默认值为0）
		byte[] arrB = new byte[8];

		// 将原始字节数组转换为8位
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}

		// 生成密钥
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

		return key;
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		java.awt.Window win = new java.awt.Window(new java.awt.Frame() {
			{
				this.setTitle("明文加密計算器--配置专用--老汪出品。");
				this.addWindowListener(new java.awt.event.WindowAdapter() {
					public void windowClosing(java.awt.event.WindowEvent e) {
						System.exit(0);
					}
				});
				this.setLayout(new GridLayout(3, 5, 4, 4));
				this.setVisible(true);
				this.setSize(600, 200);
				this.setBackground(Color.gray);
				final TextArea mw = new TextArea() {
					{
						this.setSize(10, 500);
					}
				};
				final TextArea mm = new TextArea() {
					{
						this.setSize(10, 500);
						this.setBackground(Color.cyan);
					}
				};
				this.add(mw);
				this.add(new Button() {
					{
						this.setLabel("copy top text==>build down ciphertext");
						this.setSize(10, 500);
						this.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent arg0) {
								mm.setText(DESPlus.encrypt(mw.getText()));

							}
						});
					}
				});
				this.add(mm);
			}
		});
	}

}
