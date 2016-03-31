package ru.mipt.asklector.ui.container;

/**
 * Created by Dmitry Bochkov on 14.10.2015.
 */
public class ContainerPresenterImpl implements ContainerPresenter {

    ContainerView containerView;

    public ContainerPresenterImpl(ContainerView containerView) {
        this.containerView = containerView;
    }

    @Override
    public void showSurveyIfAvailable() {
        /*if(!AskLector.isItSurveyTime())
            return;
        */
        //Survey survey = AskLector.getMixpanel().getPeople().getSurveyIfAvailable();
        //Survey survey = AskLector.getSurvey();
        /*if(survey != null){
            containerView.showSurvey(survey);
           // AskLector.deleteSurvey();
        }
        else{
            containerView.showSurveyNotAvailableMessage();
        }*/

        containerView.showGoogleSurvey();
    }

    @Override
    public void checkForSurvey() {
        /*if(AskLector.isItSurveyTime())
            containerView.showSurveyButton();
        else
            containerView.hideSurveyButton();*/
    }

    @Override
    public void showAnswersPage() {
        containerView.showAnswersPage();
    }
}
