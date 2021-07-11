package com.epam.esm.web.hateoas;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.web.contoller.CertificateController;
import com.epam.esm.web.contoller.TagController;
import com.epam.esm.web.contoller.UserController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import javax.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.afford;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class HAT {
  public CollectionModel<UserDto> makeUserLinks(List<UserDto> users, boolean isSingle) {
    if (users.size() == 0) {
      return CollectionModel.of(users);
    }
    Link selfLink;
    if (isSingle) {
      UserDto user = users.get(0);
      selfLink = linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel();
      makeOrderLinks(user.getOrders(), user.getId(), false);
      Link allOrdersLink =
          linkTo(methodOn(UserController.class).getUserOrders(user.getId(), null, null))
              .withRel("all Orders");
      user.add(allOrdersLink);
    } else {
      selfLink = linkTo(UserController.class).withSelfRel();
      for (UserDto user : users) {
        Link selfUserLink =
            linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel();
        user.add(selfUserLink);
        Link allOrdersLink =
            linkTo(methodOn(UserController.class).getUserOrders(user.getId(), null, null))
                .withRel("all Orders");
        user.add(allOrdersLink);
        makeOrderLinks(user.getOrders(), user.getId(), false);
      }
    }
    return CollectionModel.of(users, selfLink);
  }

  public CollectionModel<OrderDto> makeOrderLinks(
      List<OrderDto> orders, Integer userId, boolean isSingle) {
    if (orders.size() == 0) {
      return CollectionModel.of(orders);
    }
    Link selfLink;
    if (isSingle) {
      OrderDto order = orders.get(0);
      selfLink =
          linkTo(methodOn(UserController.class).getUserOrder(userId, order.getId())).withSelfRel();
      order.add(selfLink);
      makeCertificateLinks(Collections.singletonList(order.getCertificate()), true);
    } else {
      selfLink =
          linkTo(methodOn(UserController.class).getUserOrders(userId, null, null)).withSelfRel();
      for (OrderDto order : orders) {
        Link link =
            linkTo(methodOn(UserController.class).getUserOrder(userId, order.getId()))
                .withSelfRel();
        order.add(link);
        makeCertificateLinks(Arrays.asList(order.getCertificate()), true);
      }
    }
    return CollectionModel.of(orders, selfLink);
  }

  public CollectionModel<CertificateDto> makeCertificateLinks(
      List<CertificateDto> certificates, boolean isSingle) {
    if (certificates.size() == 0) {
      return CollectionModel.of(certificates);
    }
    Link selfLink;
    if (isSingle) {
      CertificateDto certificate = certificates.get(0);
      selfLink =
          linkTo(methodOn(CertificateController.class).getCertificate(certificate.getId()))
              .withSelfRel();
      certificate.add(selfLink);
      certificate.setTags(new HashSet<>(makeTagLinks(certificate.getTags(), false).getContent()));
    } else {
      selfLink = linkTo(CertificateController.class).withSelfRel();
      for (CertificateDto certificate : certificates) {
        Link link =
            linkTo(methodOn(CertificateController.class).getCertificate(certificate.getId()))
                .withSelfRel();
        certificate.setTags(new HashSet<>(makeTagLinks(certificate.getTags(), false).getContent()));
        certificate.add(link);
      }
    }
    return CollectionModel.of(certificates, selfLink);
  }

  public CollectionModel<TagDto> makeTagLinks(Set<TagDto> tags, boolean isSingle) {
    if (tags.size() == 0) {
      return CollectionModel.of(tags);
    }
    Link selfLink;
    List<TagDto> listTags = new ArrayList<>(tags);
    if (isSingle) {
      TagDto tag = listTags.get(0);
      selfLink = linkTo(methodOn(TagController.class).getTag(tag.getId())).withSelfRel();
      tag.add(selfLink);
    } else {
      selfLink = linkTo(TagController.class).withSelfRel();
      for (TagDto tag : listTags) {
        Link link = linkTo(methodOn(TagController.class).getTag(tag.getId())).withSelfRel();
        tag.add(link);
      }
    }
    return CollectionModel.of(listTags, selfLink);
  }
}
