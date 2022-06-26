import java.util.Random;

public class Sorter {
    public static void main(String[] args) {

        Owoc[] koszyk = {new Owoc(50,200),new Owoc(45,150),new Owoc(23,345),new Owoc(56,500),
                         new Owoc(78,120),new Owoc(12,430),new Owoc(56,300),new Owoc(43,260),
                         new Owoc(54,120),new Owoc(23,400),new Owoc(54,300),new Owoc(25,250),
                         new Owoc(78,200),new Owoc(98,100),new Owoc(71,205),new Owoc(54,405),
                         new Owoc(71,450),new Owoc(87,600),new Owoc(54,100),new Owoc(67,150)};

//--If you want random array generation, you only need to uncomment these lines--//

      /**
       Random random = new Random();
       int max = 80;
       int min = 20;
       int rangeMin = 300;
       int rangeMax = 1000;
       for(int i = 0; i<koszyk.length; i++){
           koszyk[i].kaliber = random.nextInt((max - min) + 1) + 20;
           koszyk[i].waga = Math.ceil(rangeMin + (rangeMax - rangeMin) * random.nextDouble());
       }
       */
      
        Methods metod = new Methods();
        Owoc[][][] tab = metod.mySort(koszyk);

        int bs = metod.bsrow(koszyk);
        System.out.println("Bubble sort - " + bs + " shifts!");
        int l = 0;
        for(int i = 0; i < bs; i++){
            for(int j = 0; j < koszyk.length; j++){
                tab[l][i][j].show();
            }
            System.out.println();
        }
        System.out.println();

        int ss = metod.ssrow(koszyk);
        System.out.println("Selection sort - " + ss + " shifts!");
        l = 1;
        for(int i = 0; i < ss; i++){
            for(int j = 0; j < koszyk.length; j++){
                tab[l][i][j].show();
            }
            System.out.println();
        }
        System.out.println();

        int is = metod.isrow(koszyk);
        System.out.println("Insertion sort - " + is + " shifts!");
        l = 2;
        for(int i = 0; i < is; i++){
            for(int j = 0; j < koszyk.length; j++){
                tab[l][i][j].show();
            }
            System.out.println();
        }
    }

}

class Methods{

    public void swap(Owoc[] array, int ind1, int ind2) {
        //--This method is used to move two elements in an array--//

        Owoc tmp = array[ind1];
        array[ind1] = array[ind2];
        array[ind2] = tmp;
    }

    public void fill(Owoc[] array, Owoc[][] array2, int ind1){
        //--This method is used to fullfill a specific row of a two-dimensional array with a one-dimensional array--//

        for(int j = 0; j< array.length; j++){
            array2[ind1][j] = array[j];
        }

    }

    public int bsrow(Owoc[]dane){
        //--This method, and the two following ones, are used to count the number of rows of sorting arrays, in order to then display all rows not filled with 0--//

        Owoc[]danebs = new Owoc[dane.length];
        for(int i = 0; i < danebs.length; i++){
            danebs[i] = dane[i];
        }

        boolean isneed = true;
        int bsind = 1;

        while(isneed){
            isneed = false;
            for(int j = 0; j < danebs.length - 1; j++){
                if(danebs[j].kaliber > danebs[j+1].kaliber){
                    isneed = true;
                    swap(danebs, j, j+1);
                    bsind++;
                }else if(danebs[j].kaliber == danebs[j+1].kaliber){
                    if(danebs[j].waga > danebs[j+1].waga){
                        isneed = true;
                        swap(danebs, j, j+1);
                        bsind++;
                    }
                }
            }
        }
        return bsind;
    }

    public int ssrow(Owoc[]dane){
        int ssind = 1;

        Owoc[]daness = new Owoc[dane.length];
        for(int i = 0; i < daness.length; i++){
            daness[i] = dane[i];
        }

        for (int left = 0; left < daness.length; left++) {
            int minInd = left;
            for (int i = left; i < daness.length; i++) {
                if (daness[i].kaliber< daness[minInd].kaliber) {
                    minInd = i;
                }else if(daness[i].kaliber == daness[minInd].kaliber){
                    if (daness[i].waga < daness[minInd].waga) {
                        minInd = i;
                    }
                }
            }
            swap(daness, left, minInd);
            ssind++;
        }
        return ssind;
    }

    public int isrow(Owoc[]dane){
        int isind = 1;

        for(int j = 1; j<=dane.length-1; j++) {
            for(int i=j-1; i >= 0; i--) {
                if(dane[j].kaliber < dane[i].kaliber ) {
                    swap(dane, i, j);
                    isind++;
                    j--;
                }else if(dane[j].kaliber == dane[i].kaliber){
                    if(dane[j].waga < dane[i].waga){
                        swap(dane, i,j);
                        isind++;
                        j--;
                    }
                }
            }
        }

        return isind;
    }

    public Owoc[][][] mySort(Owoc[]dane){
        //--buble sort--//

        Owoc[][] bublesort = new Owoc[(dane.length-1)*(dane.length-1)][dane.length];

        Owoc[]danebs = new Owoc[dane.length];
        for(int i = 0; i < danebs.length; i++){
            danebs[i] = dane[i];
        }

        boolean sorting = true;
        int bsind = 0;
        fill(danebs,bublesort,bsind);
        bsind++;

        while(sorting){
            sorting = false;
            for(int j = 0; j < danebs.length - 1; j++){
                if(danebs[j].kaliber > danebs[j+1].kaliber){
                    sorting = true;
                    swap(danebs, j, j+1);
                    fill(danebs,bublesort,bsind);
                    bsind++;
                }else if(danebs[j].kaliber == danebs[j+1].kaliber){
                    if(danebs[j].waga > danebs[j+1].waga){
                        sorting = true;
                        swap(danebs, j, j+1);
                        fill(danebs,bublesort, bsind);
                        bsind++;
                    }
                }
            }
        }

        //--selection sort--//

        Owoc[][] selectsort = new Owoc[(dane.length-1)*(dane.length-1)][dane.length];

        Owoc[]daness = new Owoc[dane.length];
        for(int i = 0; i < daness.length; i++){
            daness[i] = dane[i];
        }

        int ssind = 0;
        fill(daness, selectsort, ssind);
        ssind++;

        for (int left = 0; left < daness.length; left++) {
            int minInd = left;
            for (int i = left; i < daness.length; i++) {
                if (daness[i].kaliber< daness[minInd].kaliber) {
                    minInd = i;
                }else if(daness[i].kaliber == daness[minInd].kaliber){
                    if (daness[i].waga < daness[minInd].waga) {
                        minInd = i;
                    }
                }
            }
            swap(daness, left, minInd);
            fill(daness, selectsort,ssind);
            ssind++;
        }

        //--insertion sort--//

        Owoc[][] insertsort = new Owoc[(dane.length-1)*(dane.length-1)][dane.length];

        Owoc[]daneis = new Owoc[dane.length];
        for(int i = 0; i < daneis.length; i++){
            daneis[i] = dane[i];
        }

        int isind = 0;
        fill(daneis, insertsort, isind);
        isind++;

        for(int j = 1; j<=daneis.length-1; j++) {
            for(int i=j-1; i >= 0; i--) {
                if(daneis[j].kaliber < daneis[i].kaliber ) {
                    swap(daneis, i, j);
                    fill(daneis, insertsort, isind);
                    isind++;
                    j--;
                }else if(daneis[j].kaliber == daneis[i].kaliber){
                    if(daneis[j].waga < daneis[i].waga){
                        swap(daneis, i,j);
                        fill(daneis, insertsort, isind);
                        isind++;
                        j--;
                    }
                }
            }
        }

        Owoc[][][] result = new Owoc[3][][];
        result[0] = bublesort;
        result[1] = selectsort;
        result[2] = insertsort;

        return result;
    }
}

class Owoc{
    int kaliber;
    double waga;
    Owoc(int kaliber, double waga){
        this.kaliber = kaliber;
        this.waga = waga;
    }
    Owoc(int kaliber){
        this.kaliber = kaliber;
        this.waga = 100;
    }
    Owoc(double waga){
        this.kaliber = 50;
        this.waga = waga;
    }
    Owoc(){
        this.kaliber = 50;
        this.waga = 100;
    }
    void show(){
        System.out.print("[K]=" + kaliber + ",[W]=" + waga + " - ");
    }
}
