package at.qe.sepm.skeleton.ui.controllers;


import at.qe.sepm.skeleton.model.User;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("view")
public class StatisticsController {

    private PieChartModel pieModel1;

    @PostConstruct
    public void init() {
        createPieModel1();
    }

    private void createPieModels() {
        createPieModel1();
        //createPieModel2();
        //createLivePieModel();
    }

    private void createPieModel1() {
        pieModel1 = new PieChartModel();

        pieModel1.set("Brand 1", 540);
        pieModel1.set("Brand 2", 325);
        pieModel1.set("Brand 3", 702);
        pieModel1.set("Brand 4", 421);

        pieModel1.setTitle("Simple Pie");
        pieModel1.setLegendPosition("w");
        pieModel1.setShadow(false);
    }

    public void tasksDaily(User user) {


    }

    public void tasksWeekly() {

    }

    public void tasksMonthly() {

    }
}
