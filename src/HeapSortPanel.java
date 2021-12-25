import java.awt.Color;
import java.awt.Graphics;

public class HeapSortPanel extends SortPanel {
    private static final long serialVersionUID = 1L;
    private int redColumn = -1;
    private int greenColumn = -1;
    private java.util.ArrayList<Integer> heapList = new java.util.ArrayList<Integer>();

    public HeapSortPanel(String name, int sleepTime, int width, int height) {
        super(name, sleepTime, width, height);
    }

    @Override
    public void reset() {
        redColumn = -1;
        greenColumn = -1;
        heapList.clear();
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < list.length; i++) {
                add(list[i]);
                repaint();
                Thread.sleep(2 * sleepTime);
            }

            greenColumn = size;
            for (int i = list.length - 1; i > 0; i--) {
                removeAndShow(i);
                repaint();
                Thread.sleep(sleepTime);
            }
        } catch (InterruptedException e) {
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int columnWidth = (getWidth() - 4 * BORDER_WIDTH) / size;
        int columnHeight = (getHeight() - 4 * BORDER_WIDTH) / size;
        for (int i = heapList.size(); i < list.length; i++) {
            g.setColor(Color.WHITE);
            g.fillRect(2 * BORDER_WIDTH + columnWidth * i, getHeight() - list[i] * columnHeight - 2 * BORDER_WIDTH, columnWidth, list[i] * columnHeight);
            g.setColor(Color.BLACK);
            g.drawRect(2 * BORDER_WIDTH + columnWidth * i, getHeight() - list[i] * columnHeight - 2 * BORDER_WIDTH, columnWidth, list[i] * columnHeight);
        }
        for (int i = 0; i < heapList.size(); i++) {
            g.setColor(Color.CYAN);
            g.fillRect(2 * BORDER_WIDTH + columnWidth * i, getHeight() - heapList.get(i) * columnHeight - 2 * BORDER_WIDTH, columnWidth, heapList.get(i) * columnHeight);
            g.setColor(Color.BLACK);
            g.drawRect(2 * BORDER_WIDTH + columnWidth * i, getHeight() - heapList.get(i) * columnHeight - 2 * BORDER_WIDTH, columnWidth, heapList.get(i) * columnHeight);
        }

        if (greenColumn != -1) {
            for (int i = greenColumn; i < size; i++) {
                g.setColor(Color.GREEN);
                g.fillRect(2 * BORDER_WIDTH + columnWidth * i, getHeight() - list[i] * columnHeight - 2 * BORDER_WIDTH, columnWidth, list[i] * columnHeight);
                g.setColor(Color.BLACK);
                g.drawRect(2 * BORDER_WIDTH + columnWidth * i, getHeight() - list[i] * columnHeight - 2 * BORDER_WIDTH, columnWidth, list[i] * columnHeight);
            }
        }
        if (redColumn != -1) {
            g.setColor(Color.RED);
            g.fillRect(2 * BORDER_WIDTH + columnWidth * redColumn, getHeight() - heapList.get(redColumn) * columnHeight - 2 * BORDER_WIDTH, columnWidth, heapList.get(redColumn) * columnHeight);
            g.setColor(Color.BLACK);
            g.drawRect(2 * BORDER_WIDTH + columnWidth * redColumn, getHeight() - heapList.get(redColumn) * columnHeight - 2 * BORDER_WIDTH, columnWidth, heapList.get(redColumn) * columnHeight);
        }
    }

    public void add(Integer newObject) throws InterruptedException {
        heapList.add(newObject);
        repaint();
        Thread.sleep(3 * sleepTime);
        int currentIndex= heapList.size() -1;
        redColumn = currentIndex;
        while(currentIndex > 0) {
            repaint();
            Thread.sleep(3 * sleepTime);
            int parentIndex = (currentIndex -1) / 2;
            if(heapList.get(currentIndex).compareTo(heapList.get(parentIndex)) > 0) {
                repaint();
                Thread.sleep(6 * sleepTime);
                Integer temp = heapList.get(currentIndex);
                heapList.set(currentIndex, heapList.get(parentIndex));
                heapList.set(parentIndex, temp);
            } else {
                break;
            }
            currentIndex = parentIndex;
            redColumn = currentIndex;
        }
        redColumn = -1;

    }

    public void removeAndShow(int showIndex) throws InterruptedException {
        if (heapList.size() == 0 )
            return;
        repaint();
        Thread.sleep(4 * sleepTime);
        Integer removeObject = heapList.get(0);
        heapList.set(0, heapList.get(heapList.size() - 1));
        heapList.remove(heapList.size() - 1);
        greenColumn--;
        list[showIndex] = removeObject;

        int currentIndex = 0;
        while (currentIndex < heapList.size()) {
            int leftChildIndex = 2 * currentIndex + 1;
            int rightChildIndex = 2 * currentIndex + 2;

            if (leftChildIndex > heapList.size())
                break;
            int maxIndex = leftChildIndex;
            if(rightChildIndex < heapList.size()) {
                repaint();
                Thread.sleep(4 * sleepTime);
                if(heapList.get(maxIndex).compareTo(heapList.get(rightChildIndex)) < 0) {
                    maxIndex = rightChildIndex;
                }
            }
            if(heapList.get(currentIndex).compareTo(heapList.get(maxIndex)) < 0) {
                Integer temp = heapList.get(maxIndex);
                heapList.set(maxIndex, heapList.get(currentIndex));
                heapList.set(currentIndex, temp);
                currentIndex = maxIndex;
                repaint();
                Thread.sleep(4 * sleepTime);
            } else {
                break;
            }
        }
    }
}
