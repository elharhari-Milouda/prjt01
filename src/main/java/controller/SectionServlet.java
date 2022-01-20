package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import entities.Message;
import entities.Place;
import entities.Section;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.PlaceService;
import service.SectionService;

/**
 * Servlet implementation class SectionServlet
 */
public class SectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SectionService ss = new SectionService();

	public SectionServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getParameter("op") != null) {
			System.out.println(request.getParameter("op"));
			if (request.getParameter("op").equals("load")) {
				response.setContentType("application/json");
				List<Section> Sections = ss.findAll();
				PlaceService ps = new PlaceService();
				List<Place> places = ps.findAll();
				List<Place> newSect = new ArrayList<Place>();
				for (Section section : Sections) {
					Place pl = new Place();
					pl.setId(section.getId());// id : section
					pl.setEtat(section.getCode());// etat : code section
					int cpt = 0;
					int ett = 0;
					for (Place place : places) {
						if (section.getId() == place.getSection()) {
							if (place.getEtat().equals("Occupee")) {
								ett++;
							}
							cpt++;
						}
					}
					pl.setNumero(cpt);// numero : nbr de place dans chaqie section
					pl.setSection(ett);// sectionn :br de place aucuper dans chaque section
					newSect.add(pl);
					 System.out.println(pl);
				}
				 System.out.println(newSect);
				Gson json = new Gson();
				response.getWriter().write(json.toJson(newSect));

			} else if (request.getParameter("op").equals("delete")) {
				int id = Integer.parseInt(request.getParameter("id"));
				ss.delete(ss.findById(id));
				response.setContentType("application/json");
				List<Section> Sections = ss.findAll();
				Gson json = new Gson();
				response.getWriter().write(json.toJson(Sections));

			} else if (request.getParameter("op").equals("update")) {
				int id = Integer.parseInt(request.getParameter("id"));
				String code = request.getParameter("code");
				Section section = new Section(id, code);
				ss.update(section);
				response.setContentType("application/json");
				List<Section> Sections = ss.findAll();
				Gson json = new Gson();
				response.getWriter().write(json.toJson(Sections));

			} else if (request.getParameter("op").equals("add")) {
				String code = request.getParameter("code");
				boolean existe = ss.findByCode(code);
				String content = null;
				String type = null;
				String cssClass = null;
				if (!existe) {
					ss.create(new Section(code));
					content = "Section creee avec succes";
					type = "success";
					cssClass = "primary";
				} else {
					content = "Section deja existe!";
					type = "error";
					cssClass = "danger";
				}
				Message message = new Message(content, type, cssClass);
				HttpSession s = request.getSession();
				s.setAttribute("msg", message);

				response.setContentType("application/json");
				List<Section> Sections = ss.findAll();

				Gson json = new Gson();
				response.getWriter().write(json.toJson(Sections));

				response.sendRedirect("section.jsp");
			}
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
}
