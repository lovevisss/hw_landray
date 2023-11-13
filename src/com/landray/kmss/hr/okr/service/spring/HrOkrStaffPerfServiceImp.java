package com.landray.kmss.hr.okr.service.spring;

import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.okr.model.HrOkrMain;
import com.landray.kmss.hr.okr.service.IHrOkrStaffPerfService;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import net.sf.json.JSONArray;
import org.slf4j.Logger;

import java.text.DecimalFormat;

public class HrOkrStaffPerfServiceImp extends ExtendDataServiceImp implements IHrOkrStaffPerfService {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(HrOkrStaffPerfServiceImp.class);

    private IsysNotifyMainCoreService sysNotifyMainCoreService;
    @Override
    public List<HrOkrStaffPerf> findByFdMain(HrOkrMain fdMain) throws Exception {
        return null;
    }

    /**
     * <p>根据目标主表ID和目标组ID查询目标评分记录</p>  总表  包含所有人 被评测人名单
     * @param fdId
     * @param fdGroupId
     * @return
     * @throws Exception
     */
    @Override
    public List<HrOkrStaffPerf> findByFdMain(String fdId, String fdGroupId) throws Exception {
        return null;
    }


    @Override
    public IBaseModel convertBizFormToModel(IBaseModel form, IBaseModel model, ConvertorContext context) throws Exception {
        if(form instanceof HrOkrStaffPerfForm){
            HrOkrStaffPerfForm okrStaffPerfForm = (HrOkrStaffPerfForm) form;
            AutoArrayList evalIndexList = new AutoArrayList(HrOkrStaffIndexForm.class);
        }
        return null;
    }



    @Override
    public void saveStaffGrade(String fdId, String gradeId, String mutilGradeId, JSONArray array) throws Exception {
        // TODO Auto-generated method stub
        SysOrgPerson currPerson = UserUtil.getUser(); // 当前登录人
        DecimalFormat df = new DecimalFormat("0.00");
        HrOkrStaffPerf modeObj = (HrOkrStaffPerf) this.findByPrimaryKey(fdId);  //被评测人
        Integer currGradeStage = modeObj.getFdGradeStage(); // 当前评分阶段

        HrOkrStaffGrade grade = getHrOkrStaffGradeServiceImp().findByPrimaryKey(gradeId); //第二个参数  评分的Id 在这里获取到的的具体在哪个节点的信息 fdOrder fdGradeType
        if(grade == null || StringUtil.isNull(grade.getFdId())){
            throw new Exception("评分节点不存在");
        }

        HrOkrStaffMutilGrade mutilGrade = null;


        // 保存评分记录

    }

}
