package mc322.lab07.control.construtor;

import java.util.Random;

import mc322.lab07.model.elemento.Porcao;
import mc322.lab07.model.elemento.Elemento;
import mc322.lab07.model.elemento.Fogo;
import mc322.lab07.model.elemento.Livre;
import mc322.lab07.model.elemento.Muralha;

public class GeradorDeElementos {
	private static int limiteSuperiorNumeroSorteado = 1000;
	private static int valoresLimiteElementosSemPiloto[] = new int[]{ 810, 165, 15, 10 }; // livre, muralha, fogo, pocao
	private static Random random = new Random();
	
	
	public Elemento geradorAleatorioDeElementosSemPiloto(int lin, int col)
	{
		int valor = random.nextInt(limiteSuperiorNumeroSorteado);
		
		while(true)
		{
			for(int i=0; i<valoresLimiteElementosSemPiloto.length; i++)
			{
				int valorLimite = valoresLimiteElementosSemPiloto[i];
				if(valor < valorLimite)
				{
					if(i == 0)
					{
						return new Livre(lin, col);
					}
					if(i == 1)
					{
						return new Muralha(lin, col);
					}
					if(i == 2)
					{
						return new Fogo(lin, col);
					}
					if(i == 3)
					{
						return new Porcao(lin, col);
					}
				}
				valor -= valorLimite;
			}
		}		
	}	
}
