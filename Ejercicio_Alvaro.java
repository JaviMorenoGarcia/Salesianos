package Binarios;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Ejercicio_Alvaro {
	
	private static final int BYTES_VENTA = (3*Integer.SIZE + Double.SIZE)/8;
	
	public static void escribirEnPosicion(String nombreFichero, int posicion, int producto, int cliente, double precio) {
		try {
			RandomAccessFile f = new RandomAccessFile(nombreFichero, "rw");
			try {
				posicionar(f, posicion-1);
				f.writeInt(producto);
				f.writeInt(cliente);
				f.writeDouble(precio);
				f.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void posicionar(RandomAccessFile f, int numVentas) {
		try {
			f.seek(BYTES_VENTA*(numVentas - 1));
		} catch (IOException e) {
			System.out.println("Error al posicionarnos en el fichero");
		}
	}
	
	public static void escribir(RandomAccessFile f, int producto, int cliente,
			int cantidad, double precio) {
		try {
			f.writeInt(producto);
			f.writeInt(cliente);
			f.writeInt(cantidad);
			f.writeDouble(precio);
		} catch (IOException e) {
			System.out.println("Error al escribir en el fichero");
		}
	}
	
	public static void crearFichero(String nombre) {
		try {
			RandomAccessFile f = new RandomAccessFile(nombre, "rw");
			f.setLength(0);
			escribir(f, 102, 14201, 7, 641.12);
			escribir(f, 107, 9641, 25, 604.12);
			escribir(f, 282, 14201, 6, 604.3);
			escribir(f, 107, 61734, 10, 601);
			escribir(f, 107, 61734, 8, 600.12);
			f.close();
		} catch (FileNotFoundException e) {
			System.out.println("El fichero no ha podido ser abierto");
		} catch (IOException e) {
			System.out.println("Error en la operación E/S con el fichero");
		}
	}
	
	public static void leerFichero(String nombre) {
		try {
			RandomAccessFile f = new RandomAccessFile(nombre, "r");
			try {
				System.out.println("VENTA  PRODUCTO  CLIENTE  CANTIDAD  PRECIO");
				System.out.println("=====  ========  =======  ========  ======");
				int cuenta = 0;
				while(true) {
					int producto = f.readInt();
					int cliente = f.readInt();
					int cantidad = f.readInt();
					double precio = f.readDouble();
					cuenta++;
					System.out.printf("%4d%9d%10d%9d%10.2f\n", cuenta, producto, cliente,
							cantidad, precio);
				}
			} catch (EOFException e) {}
			f.close();
		} catch (FileNotFoundException e) {
			System.out.println("El fichero no ha podido ser abierto");
		} catch (IOException e) {
			System.out.println("Error en la operación E/S con el fichero");
		}	
	}
	
	public static void inspeccionar(String nombre, int numero) {
		try {
			RandomAccessFile f = new RandomAccessFile(nombre, "r");
			posicionar(f, numero);
			int producto = f.readInt();
			int cliente = f.readInt();
			int cantidad = f.readInt();
			double precio = f.readDouble();
			System.out.printf("INSPECCIONAMOS VENTA Nº " + numero + 
					" --> " + "%9d%10d%9d%10.2f\n", producto, cliente,
					cantidad, precio);
			f.close();
		} catch (FileNotFoundException e) {
			System.out.println("El fichero no ha podido ser abierto");
		} catch (IOException e) {
			System.out.println("Error en la lectura de datos del fichero");
		}
		
	}
	
	public static void main(String[] args) {
		String nombreFichero = "Files/ventas.bin";
		crearFichero(nombreFichero);
		leerFichero(nombreFichero);
		inspeccionar(nombreFichero, 3);
		escribirEnPosicion(nombreFichero, 4, 3, 3, 2);
		inspeccionar(nombreFichero, 4);
	}
}
