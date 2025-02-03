package com.chw.shopping.common.interceptor;

import com.chw.shopping.dao.oracle.MaMenuDAO;
import com.chw.shopping.ma.menu.service.MaMenuVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class MaInterceptor  implements HandlerInterceptor {

    private final MaMenuDAO menuDAO;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("========== MaInterceptor preHandle Start ==========");
        HttpSession session = request.getSession();

        // 1차 메뉴
        MaMenuVO menuVO1 = new MaMenuVO();
        menuVO1.setMenuDivn("ma");
        menuVO1.setUpperCd("");
        List<MaMenuVO> menuList1 = menuDAO.selectMenuList(menuVO1);
        menuVO1.setMenuList(menuList1);

        // 2차 메뉴
        for(MaMenuVO menu2 : menuVO1.getMenuList()) {
            menu2.setMenuDivn("ma");
            menu2.setUpperCd(menu2.getMenuCd());
            List<MaMenuVO> menuList2 = menuDAO.selectMenuList(menu2);
            menu2.setMenuList(menuList2);

            // 3차 메뉴
            for(MaMenuVO menu3 : menu2.getMenuList()) {
                menu3.setMenuDivn("ma");
                menu3.setUpperCd(menu3.getMenuCd());
                List<MaMenuVO> menuList3 = menuDAO.selectMenuList(menu3);
                menu3.setMenuList(menuList3);
                menu2.setMaMenuVO(menu3);
            }
            menuVO1.setMaMenuVO(menu2);
        }

        session.setAttribute("menuList", menuVO1.getMenuList());
        log.info("========== MaInterceptor preHandle End ==========");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
