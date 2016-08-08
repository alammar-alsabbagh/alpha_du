package com.procasy.dubarah_nocker;

import com.procasy.dubarah_nocker.Model.Responses.SkillsResponse;

import java.util.List;

/**
 * Created by DELL on 08/08/2016.
 */
public interface SkillsController {

    void onAdapterUpdated(List<SkillsResponse> newdata);

}
