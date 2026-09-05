public class ContaBancaria {

    public String numero;
    public double saldo;

    public double depositar(double valor){
        this.saldo += valor;
        return this.saldo;
    }

    public double sacar(double valor){
        this.saldo -= valor;
        return this.saldo;
    }


    public double saldoAtual(){
        return this.saldo;
    }

}
