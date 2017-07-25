package org.launchcode.controllers;

import org.launchcode.models.Job;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        model.addAttribute(jobData.findById(id));
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        if(errors.hasErrors()){
            model.addAttribute(jobForm);
            return "new-job";
        }
        Job job = new Job(jobForm.getName(), JobData.getInstance().getEmployers().findById(jobForm.getEmployerId()), JobData.getInstance().getLocations().findById(jobForm.getLocationId()), JobData.getInstance().getPositionTypes().findById(jobForm.getPositionTypeId()), JobData.getInstance().getCoreCompetencies().findById(jobForm.getCoreCompetencyId()));
        JobData.getInstance().add(job);

        return "redirect:?id=" + job.getId();

    }
}