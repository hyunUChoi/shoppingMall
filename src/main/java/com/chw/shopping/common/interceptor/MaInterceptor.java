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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class MaInterceptor  implements HandlerInterceptor {

    private final MaMenuDAO menuDAO;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("========== MaInterceptor preHandle Start ==========");
        HttpSession session = request.getSession();
        String requestURI = request.getRequestURI();

        MaMenuVO maMenuVO = new MaMenuVO();
        maMenuVO.setMenuDivn("ma");
        List<MaMenuVO> flatMenuList = menuDAO.selectMenuList(maMenuVO);

        Map<String, MaMenuVO> menuMap = new HashMap<>();
        List<MaMenuVO> menuList = new ArrayList<>();

        for (MaMenuVO menu : flatMenuList) {
            menuMap.put(menu.getMenuCd(), menu);

            if (menu.getUpperCd() == null || menu.getUpperCd().isEmpty()) {
                menuList.add(menu);
            } else {
                MaMenuVO parent = menuMap.get(menu.getUpperCd());
                if (parent != null) {
                    if(parent.getMenuList() == null) {
                        parent.setMenuList(new ArrayList<>());
                    }
                    parent.getMenuList().add(menu);
                }
            }
        }

        session.setAttribute("maMenuList", menuList);
        session.setAttribute("requestURI", requestURI);
        log.info("========== MaInterceptor preHandle End ==========");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
