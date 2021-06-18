package mc322.lab07.control.controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import mc322.lab07.control.construtor.IConstrutorControle;
import mc322.lab07.model.circuito.ICircuitoControle;
import mc322.lab07.model.elemento.Elemento;
import mc322.lab07.model.elemento.IPilotoControle;
import mc322.lab07.view.painel.IPainelControle;

public class Controle implements IControle, ActionListener, KeyListener
{	
	private Boolean comecar = false;
	private Boolean terminar = false;
	
	IPilotoControle ipilo = null;
	IPainelControle ipain = null;
	ICircuitoControle icirc = null;
	IConstrutorControle icons = null;
	
	int counter = 0;
			
	public void conectar(ICircuitoControle icircCont)
	{
		this.icirc = icircCont;
	}
	
	
	public void conectar(IPilotoControle ipiloCont)
	{
		this.ipilo = ipiloCont;
	}
	
	
	public void conectar(IPainelControle ipain)
	{
		this.ipain = ipain;
	}	
	
	
	public void conectar(IConstrutorControle icons)
	{
		this.icons = icons;		
	}
	

	public void bloquearExecucao()
	{
		while(true)
		{
			if(comecar)
			{
				break;
			}
			try 
			{
				Thread.sleep(500);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}				
	}
	
	
	public void tentarDecrementarPoderzin()
	{
		int poderzin = ipilo.getPoderzin();
		
		if(poderzin > 0) // Atualizando a duracao da pocao
		{
			poderzin--;
			ipilo.setPoderzin(poderzin);
		}
		if(poderzin == 0)
		{
			poderzin--;
			ipilo.setPoderzin(poderzin);
			ipilo.setImagemIcon1();
		}
	}
	
	
	public void gerenciarInteracaoPilotoMura()
	{
		int lin = ipilo.getLin();
		int col = ipilo.getCol();	
		Elemento elem = icirc.getElemento(lin, col);
		
		if(elem.getSimbolo().equals("M"))
		{
			if(lin+1 >= icirc.getMaxLin())
			{
				gerenciarFinalDeJogo("Muro");
			}
			ipilo.moverParaBaixo();
		}
	}
	
	
	public void gerenciarInteracaoPilotoFogo()
	{
		int lin = ipilo.getLin();
		int col = ipilo.getCol();
		Elemento elem = icirc.getElemento(lin, col);
		
		if(elem.getSimbolo().equals("F") && ipilo.getPoderzin() <= 0)
		{
			gerenciarFinalDeJogo("Fogo");
		}
	}
	
	
	public void gerenciarInteracaoPilotoPorcao()
	{
		int lin = ipilo.getLin();
		int col = ipilo.getCol();	
		Elemento elem = icirc.getElemento(lin, col);
		
		if(elem.getSimbolo().equals("C"))
		{
			ipilo.setPoderzin(60);  // Tempo de duracao da pocao
			ipilo.setImagemIcon2();
		}		
	}
	
	
	public void gerenciarFinalDeJogo(String motivo) 
	{
		if(motivo.equals("Muro"))
		{
			ipain.atualizarStatus("Game over, você foi consumido pelo fogo!!!");
		}
		
		if(motivo.equals("Fogo"))
		{
			ipain.atualizarStatus("Game over, você explodiu!!!");			
		}
		
		ipilo.setImagemIcon3();
		comecar = false;
		terminar = true;
	}
	
	
	public void gerenciarRecomecoDeJogo()
	{
		icons.reiniciarElementosNaMatrizDoCircuito();
		counter = 0;
		ipilo.setImagemIcon1();
		ipain.atualizarStatus("Esperando início do jogo!!!");
	}
	
	
	public void comecarJogo()
	{
		ipain.addPlayActionListener(this);
		ipain.addPlayKeyListener(this);
		ipain.mostrarPainel();
		ipain.atualizarImagemCircuitoPainel();
		ipain.atualizarImagemPilotoPainel();
		ipain.atualizarPainel();
		
		ipain.atualizarStatus("Esperando início do jogo!!!");

		int maxCounter = 500;
		
		for(counter=0; counter<maxCounter; counter++)
		{	
			bloquearExecucao();
			tentarDecrementarPoderzin();
			icons.avancarElementosUmaLinhaNoCircuito();

			this.gerenciarInteracaoPilotoMura();
			this.gerenciarInteracaoPilotoFogo();
			this.gerenciarInteracaoPilotoPorcao();			
			
			ipain.atualizarImagemCircuitoPainel();
			ipain.atualizarImagemPilotoPainel();
			ipain.atualizarScore(counter*10 + 10);
			ipain.atualizarPainel();
			
			try 
			{	
				float a = (1 - 150)/(float)maxCounter;
				float b = 150;
				Thread.sleep((int)(a*counter + b));
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			
			if(this.terminar)
			{
				bloquearExecucao();
			}
		}		
		ipain.atualizarStatus("Parabéns, você venceu!!!");
	}


	public void keyTyped(KeyEvent e) {
		String key = "" + e.getKeyChar() + "";

		if(comecar)
		{
			if(key.toLowerCase().equals("w"))
			{
				tentarMoverParaCima();
			}
			
			if(key.toLowerCase().equals("s"))
			{
				tentarMoverParaBaixo();
			}
			
			if(key.toLowerCase().equals("a"))
			{
				tentarMoverParaEsquerda();
			}

			if(key.toLowerCase().equals("d"))
			{
				tentarMoverParaDireita();
			}

		}
		
		if(key.toLowerCase().equals("r"))
		{
			this.gerenciarRecomecoDeJogo();
		}
		
		this.gerenciarInteracaoPilotoFogo();
		this.gerenciarInteracaoPilotoPorcao();	
		this.gerenciarInteracaoPilotoMura();
			
		ipain.atualizarImagemCircuitoPainel();
		ipain.atualizarImagemPilotoPainel();
		ipain.atualizarPainel();
	}
	
	
	public void tentarMoverParaCima()
	{
		int lin = ipilo.getLin();
		int col = ipilo.getCol();
		Elemento elem = icirc.getElemento(lin, col);
		if(elem.getSimbolo().equals("M"))		
			ipilo.moverParaBaixo();
		
		lin = ipilo.getLin();
		col = ipilo.getCol();
		lin -= 1;
		elem = icirc.getElemento(lin, col);
		if(!elem.getSimbolo().equals("M"))
			ipilo.moverParaCima();
	}
	
	
	public void tentarMoverParaBaixo()
	{
		int lin = ipilo.getLin();
		int col = ipilo.getCol();
		Elemento elem = icirc.getElemento(lin, col);
		if(elem.getSimbolo().equals("M"))		
			ipilo.moverParaCima();
		
		lin = ipilo.getLin();
		col = ipilo.getCol();
		lin += 1;
		elem = icirc.getElemento(lin, col);
		if(!elem.getSimbolo().equals("M"))
			ipilo.moverParaBaixo();
	}
	
	
	public void tentarMoverParaEsquerda()
	{
		int lin = ipilo.getLin();
		int col = ipilo.getCol();
		col -= 1;
		Elemento elem = icirc.getElemento(lin, col);
		if(!elem.getSimbolo().equals("M"))
			ipilo.moverParaEsquerda();	
	}
	
	
	public void tentarMoverParaDireita()
	{		
		int lin = ipilo.getLin();
		int col = ipilo.getCol();
		col += 1;
		Elemento elem = icirc.getElemento(lin, col);
		if(!elem.getSimbolo().equals("M"))
			ipilo.moverParaDireita();		
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub			
	}
	
	
	public void actionPerformed(ActionEvent evento)
	{	
		if(counter == 0)
		{
			ipain.atualizarStatus("Boa Sorte!!!");
			comecar = true;		
		}
	}
}