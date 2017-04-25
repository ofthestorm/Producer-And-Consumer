/**
 *
 * @author Keke
 */

package pers.keke.pnc;


import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;


public class WindowController {

    @FXML private TextArea textArea;

    @FXML private ImageView doughnut0;
    @FXML private ImageView doughnut1;
    @FXML private ImageView doughnut2;
    @FXML private ImageView doughnut3;
    @FXML private ImageView doughnut4;
    @FXML private ImageView doughnut5;
    @FXML private ImageView doughnut6;
    @FXML private ImageView doughnut7;
    @FXML private ImageView doughnut8;

    @FXML private ImageView cake0;
    @FXML private ImageView cake1;
    @FXML private ImageView cake2;
    @FXML private ImageView cake3;
    @FXML private ImageView cake4;
    @FXML private ImageView cake5;
    @FXML private ImageView cake6;
    @FXML private ImageView cake7;
    @FXML private ImageView cake8;

    @FXML private ImageView cakeFromChef;
    @FXML private ImageView doughnutFromChef;
    @FXML private ImageView cakeForC1;
    @FXML private ImageView cakeForC2;
    @FXML private ImageView doughnutForC1;
    @FXML private ImageView doughnutForC2;

    private Container container = new Container();
    private Container.Dessert [] array = container.getDesserts();

    private ImageView [] doughnuts = new ImageView[container.getContainerSize()];
    private ImageView [] cakes = new ImageView[container.getContainerSize()];
    

//    Thread produceThread1 = new Thread(new Producer(container), "Chef     1");
//    Thread produceThread2 = new Thread(new Producer(container), "Chef     2");
//    Thread consumeThread1 = new Thread(new Consumer(container), "Customer 1");
//    Thread consumeThread2 = new Thread(new Consumer(container), "Customer 2");

    Producer producer1 = new Producer(container);
    Producer producer2 = new Producer(container);
    Consumer consumer1 = new Consumer(container);
    Consumer consumer2 = new Consumer(container);

    Thread produceThread1 = new Thread(producer1, "Chef     1");
    Thread produceThread2 = new Thread(producer2, "Chef     2");
    Thread consumeThread1 = new Thread(consumer1, "Customer 1");
    Thread consumeThread2 = new Thread(consumer2, "Customer 2");

    UpdateText updateText = new UpdateText(this);
    Thread updateTextThread = new Thread(updateText,"Update TextArea");


    public void startPressed() throws InterruptedException {
        System.out.println("start pressed");
        if(!produceThread1.isAlive() && !produceThread2.isAlive()
                && !consumeThread1.isAlive() && !consumeThread2.isAlive()) {
            producer1.setExit(false);
            producer2.setExit(false);
            consumer1.setExit(false);
            consumer2.setExit(false);

            produceThread1.start();
            produceThread2.start();
            consumeThread1.start();
            consumeThread2.start();

//            Thread update = new Thread(() -> {
//                    try {
//                        Thread.sleep(1000);
//                        updateText();
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//            });
//         //   update.setDaemon(true);
//            update.start();
            updateTextThread.start();

        } else {
            producer1.setExit(false);
            producer2.setExit(false);
            consumer1.setExit(false);
            consumer2.setExit(false);
            produceThread1.join();
            produceThread2.join();
            consumeThread1.join();
            consumeThread2.join();
        }

    }

    public void stopPressed() {
        System.out.println("stop pressed");
        try {
            if(produceThread1.isAlive() && produceThread2.isAlive()
                    && consumeThread1.isAlive() && consumeThread2.isAlive()) {
//                produceThread1.interrupt();
//                produceThread2.interrupt();
//                consumeThread1.interrupt();
//                consumeThread2.interrupt();
                producer1.setExit(true);
                producer2.setExit(true);
                consumer1.setExit(true);
                consumer2.setExit(true);

                updateText.setExit(true);

            } else {
                //
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

   // private String textContent = "";

    public void updateText() {
    //    textContent += container.getLog() + "\n" ;
    //    textArea.setText(textContent);
        textArea.setText(container.getLog());
        textArea.setScrollTop(Double.MAX_VALUE);//auto scroll to the bottom

    }
//    textArea.textProperty().addListener(new ChangeListener<Object>() {
//        @Override
//        public void changed(ObservableValue<?> observable, Object oldValue,
//                Object newValue) {
//            textArea.setScrollTop(Double.MAX_VALUE); //this will scroll to the bottom
//            //use Double.MIN_VALUE to scroll to the top
//        }
//    });



    private void updateImage() {
        for(int i = 0 ; i < container.getContainerSize() ; i++) {
            switch (array[i]) {
                case doughnut: {
                    //doughnut(i).setvisible(true)
                } break;
                case cake: {
                    //cake(i).setvisible(true)
                } break;
                case none: {
                    //doughnut(i).setvisible(false)
                    //cake(i).setvisible(false)
                } break;
                default: break;
            }
        }
    }


}


class UpdateText implements Runnable {
    private WindowController controller;

    public UpdateText(WindowController controller) {
        this.controller = controller;
    }

    private boolean exit = false;

    public void setExit(boolean flag) {
        exit = flag;
    }

    @Override
    public void run() {
        while (!exit) {
            try {
                controller.updateText();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
