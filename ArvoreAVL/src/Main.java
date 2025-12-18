import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //Arvore binaria AVL (Adelson-Velsky e Landis)
        //Uma arvore que se manter balanceada automaticamente

        Scanner sc = new Scanner(System.in);
        Node<Integer> root = null;
        System.out.print("Deseja inserir? (1 - Sim / 2 - Não): ");
        int opc = sc.nextInt();

        while (opc == 1){
            System.out.print("Informe o valor: ");
            int value = sc.nextInt();
            root = insert(root, value);
            System.out.print("Deseja inserir? (1 - Sim / 2 - Não): ");
            opc = sc.nextInt();
        }


        System.out.println("Value height tree: " +  root.getHeight());

        sc.close();
    }


    public static Node<Integer> insert(Node<Integer> no, int value){
        if (no == null){
            no = new Node<>();
            no.setValue(value);
            no.setHeight(0);
            return no;
        }

        if (value <= no.getValue()){
            no.setLeft(insert(no.getLeft(), value));
        }
        else {
            no.setRight(insert(no.getRight(), value));
        }

        no.setHeight(Math.max(altura(no.getLeft()), altura(no.getRight())) + 1);

        Node<Integer> node = varificarRotacao(fatorBalanceamento(no), no);

        return node;
    }

    private static Node<Integer> varificarRotacao(int fatorBalanceamento, Node<Integer> no) {
        if (fatorBalanceamento > 1){
            if (altura(no.getLeft()) == -1){
                //return rotacaoEsquerdaDireita(no);
            }
           // return rotacaoDireita(no);
        }
        if (fatorBalanceamento < -1) {
            if (altura(no.getRight()) == 1){
               // return rotacaoDireitaEsquerda(no);
            }
           // return rotacaoEsquerda(no);
        }
        return no;
    }

    private static int fatorBalanceamento(Node<Integer> no) {
        if (no != null){
            return altura(no.getLeft()) - altura(no.getRight());
        }
        return 0;
    }

    public static int altura(Node<Integer> no){
        if (no == null) return -1;
        return no.getHeight();
    }
}