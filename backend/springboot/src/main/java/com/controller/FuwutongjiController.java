package com.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.FuwutongjiEntity;
import com.entity.view.FuwutongjiView;

import com.service.FuwutongjiService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 服务统计
 * 后端接口
 * @author 
 * @email 
 */
@RestController
@RequestMapping("/fuwutongji")
public class FuwutongjiController {
    @Autowired
    private FuwutongjiService fuwutongjiService;




    



    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,FuwutongjiEntity fuwutongji,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("zhiyuanzhe")) {
			fuwutongji.setZhiyuanzhezhanghao((String)request.getSession().getAttribute("username"));
		}
		if(tableName.equals("zhiyuanxiaozuzhang")) {
			fuwutongji.setXiaozubianhao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<FuwutongjiEntity> ew = new EntityWrapper<FuwutongjiEntity>();

		PageUtils page = fuwutongjiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, fuwutongji), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前台列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,FuwutongjiEntity fuwutongji, 
		HttpServletRequest request){
        EntityWrapper<FuwutongjiEntity> ew = new EntityWrapper<FuwutongjiEntity>();

		PageUtils page = fuwutongjiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, fuwutongji), params), params));
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( FuwutongjiEntity fuwutongji){
       	EntityWrapper<FuwutongjiEntity> ew = new EntityWrapper<FuwutongjiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( fuwutongji, "fuwutongji")); 
        return R.ok().put("data", fuwutongjiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(FuwutongjiEntity fuwutongji){
        EntityWrapper< FuwutongjiEntity> ew = new EntityWrapper< FuwutongjiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( fuwutongji, "fuwutongji")); 
		FuwutongjiView fuwutongjiView =  fuwutongjiService.selectView(ew);
		return R.ok("查询服务统计成功").put("data", fuwutongjiView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        FuwutongjiEntity fuwutongji = fuwutongjiService.selectById(id);
        return R.ok().put("data", fuwutongji);
    }

    /**
     * 前台详情
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        FuwutongjiEntity fuwutongji = fuwutongjiService.selectById(id);
        return R.ok().put("data", fuwutongji);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody FuwutongjiEntity fuwutongji, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(fuwutongji);
        fuwutongjiService.insert(fuwutongji);
        return R.ok();
    }
    
    /**
     * 前台保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody FuwutongjiEntity fuwutongji, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(fuwutongji);
        fuwutongjiService.insert(fuwutongji);
        return R.ok();
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody FuwutongjiEntity fuwutongji, HttpServletRequest request){
        //ValidatorUtils.validateEntity(fuwutongji);
        fuwutongjiService.updateById(fuwutongji);//全部更新
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        fuwutongjiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	






    /**
     * （按值统计）
     */
    @RequestMapping("/value/{xColumnName}/{yColumnName}")
    public R value(@PathVariable("yColumnName") String yColumnName, @PathVariable("xColumnName") String xColumnName,HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("xColumn", xColumnName);
        params.put("yColumn", yColumnName);
        EntityWrapper<FuwutongjiEntity> ew = new EntityWrapper<FuwutongjiEntity>();
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("zhiyuanzhe")) {
            ew.eq("zhiyuanzhezhanghao", (String)request.getSession().getAttribute("username"));
		}
		if(tableName.equals("zhiyuanxiaozuzhang")) {
            ew.eq("xiaozubianhao", (String)request.getSession().getAttribute("username"));
		}
        List<Map<String, Object>> result = fuwutongjiService.selectValue(params, ew);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(Map<String, Object> m : result) {
            for(String k : m.keySet()) {
                if(m.get(k) instanceof Date) {
                    m.put(k, sdf.format((Date)m.get(k)));
                }
            }
        }
        return R.ok().put("data", result);
    }

    /**
     * （按值统计(多)）
     */
    @RequestMapping("/valueMul/{xColumnName}")
    public R valueMul(@PathVariable("xColumnName") String xColumnName,@RequestParam String yColumnNameMul, HttpServletRequest request) {
        String[] yColumnNames = yColumnNameMul.split(",");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("xColumn", xColumnName);
        List<List<Map<String, Object>>> result2 = new ArrayList<List<Map<String,Object>>>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        EntityWrapper<FuwutongjiEntity> ew = new EntityWrapper<FuwutongjiEntity>();
        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("zhiyuanzhe")) {
            ew.eq("zhiyuanzhezhanghao", (String)request.getSession().getAttribute("username"));
        }
        if(tableName.equals("zhiyuanxiaozuzhang")) {
            ew.eq("xiaozubianhao", (String)request.getSession().getAttribute("username"));
        }
        for(int i=0;i<yColumnNames.length;i++) {
            params.put("yColumn", yColumnNames[i]);
            List<Map<String, Object>> result = fuwutongjiService.selectValue(params, ew);
            for(Map<String, Object> m : result) {
                for(String k : m.keySet()) {
                    if(m.get(k) instanceof Date) {
                        m.put(k, sdf.format((Date)m.get(k)));
                    }
                }
            }
            result2.add(result);
        }
        return R.ok().put("data", result2);
    }

    /**
     * （按值统计）时间统计类型
     */
    @RequestMapping("/value/{xColumnName}/{yColumnName}/{timeStatType}")
    public R valueDay(@PathVariable("yColumnName") String yColumnName, @PathVariable("xColumnName") String xColumnName, @PathVariable("timeStatType") String timeStatType,HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("xColumn", xColumnName);
        params.put("yColumn", yColumnName);
        params.put("timeStatType", timeStatType);
        EntityWrapper<FuwutongjiEntity> ew = new EntityWrapper<FuwutongjiEntity>();
        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("zhiyuanzhe")) {
            ew.eq("zhiyuanzhezhanghao", (String)request.getSession().getAttribute("username"));
        }
        if(tableName.equals("zhiyuanxiaozuzhang")) {
            ew.eq("xiaozubianhao", (String)request.getSession().getAttribute("username"));
        }
        List<Map<String, Object>> result = fuwutongjiService.selectTimeStatValue(params, ew);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(Map<String, Object> m : result) {
            for(String k : m.keySet()) {
                if(m.get(k) instanceof Date) {
                    m.put(k, sdf.format((Date)m.get(k)));
                }
            }
        }
        return R.ok().put("data", result);
    }

    /**
     * （按值统计）时间统计类型(多)
     */
    @RequestMapping("/valueMul/{xColumnName}/{timeStatType}")
    public R valueMulDay(@PathVariable("xColumnName") String xColumnName, @PathVariable("timeStatType") String timeStatType,@RequestParam String yColumnNameMul,HttpServletRequest request) {
        String[] yColumnNames = yColumnNameMul.split(",");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("xColumn", xColumnName);
        params.put("timeStatType", timeStatType);
        List<List<Map<String, Object>>> result2 = new ArrayList<List<Map<String,Object>>>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        EntityWrapper<FuwutongjiEntity> ew = new EntityWrapper<FuwutongjiEntity>();
        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("zhiyuanzhe")) {
            ew.eq("zhiyuanzhezhanghao", (String)request.getSession().getAttribute("username"));
        }
        if(tableName.equals("zhiyuanxiaozuzhang")) {
            ew.eq("xiaozubianhao", (String)request.getSession().getAttribute("username"));
        }
        for(int i=0;i<yColumnNames.length;i++) {
            params.put("yColumn", yColumnNames[i]);
            List<Map<String, Object>> result = fuwutongjiService.selectTimeStatValue(params, ew);
            for(Map<String, Object> m : result) {
                for(String k : m.keySet()) {
                    if(m.get(k) instanceof Date) {
                        m.put(k, sdf.format((Date)m.get(k)));
                    }
                }
            }
            result2.add(result);
        }
        return R.ok().put("data", result2);
    }

    /**
     * 分组统计
     */
    @RequestMapping("/group/{columnName}")
    public R group(@PathVariable("columnName") String columnName,HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("column", columnName);
        EntityWrapper<FuwutongjiEntity> ew = new EntityWrapper<FuwutongjiEntity>();
        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("zhiyuanzhe")) {
            ew.eq("zhiyuanzhezhanghao", (String)request.getSession().getAttribute("username"));
        }
        if(tableName.equals("zhiyuanxiaozuzhang")) {
            ew.eq("xiaozubianhao", (String)request.getSession().getAttribute("username"));
        }
        List<Map<String, Object>> result = fuwutongjiService.selectGroup(params, ew);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(Map<String, Object> m : result) {
            for(String k : m.keySet()) {
                if(m.get(k) instanceof Date) {
                    m.put(k, sdf.format((Date)m.get(k)));
                }
            }
        }
        return R.ok().put("data", result);
    }




    /**
     * 总数量
     */
    @RequestMapping("/count")
    public R count(@RequestParam Map<String, Object> params,FuwutongjiEntity fuwutongji, HttpServletRequest request){
        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("zhiyuanzhe")) {
            fuwutongji.setZhiyuanzhezhanghao((String)request.getSession().getAttribute("username"));
        }
        if(tableName.equals("zhiyuanxiaozuzhang")) {
            fuwutongji.setXiaozubianhao((String)request.getSession().getAttribute("username"));
        }
        EntityWrapper<FuwutongjiEntity> ew = new EntityWrapper<FuwutongjiEntity>();
        int count = fuwutongjiService.selectCount(MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, fuwutongji), params), params));
        return R.ok().put("data", count);
    }



}
