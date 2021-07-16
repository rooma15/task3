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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class HateoasHelper {
  public CollectionModel<UserDto> makeUserLinks(List<UserDto> users) {
    if (users.isEmpty()) {
      return CollectionModel.of(users);
    }
    Link selfLink = linkTo(UserController.class).withSelfRel();
    for (UserDto user : users) {
      Link selfUserLink =
          linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel();
      user.add(selfUserLink);
      Link allOrdersLink =
          linkTo(methodOn(UserController.class).getUserOrders(user.getId(), null, null))
              .withRel("all Orders");
      user.add(allOrdersLink);
      makeOrderLinks(user.getOrders(), user.getId());
    }
    return CollectionModel.of(users, selfLink);
  }

  public UserDto makeUserLinks(UserDto user) {
    Link selfLink = linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel();
    makeOrderLinks(user.getOrders(), user.getId());
    Link allOrdersLink =
        linkTo(methodOn(UserController.class).getUserOrders(user.getId(), null, null))
            .withRel("all Orders");
    user.add(allOrdersLink);
    user.add(selfLink);
    return user;
  }

  public CollectionModel<OrderDto> makeOrderLinks(List<OrderDto> orders, Integer userId) {
    if (orders.isEmpty()) {
      return CollectionModel.of(orders);
    }
    Link selfLink =
        linkTo(methodOn(UserController.class).getUserOrders(userId, null, null)).withSelfRel();
    for (OrderDto order : orders) {
      Link link =
          linkTo(methodOn(UserController.class).getUserOrder(userId, order.getId())).withSelfRel();
      order.add(link);
      makeCertificateLinks(order.getCertificate());
    }
    return CollectionModel.of(orders, selfLink);
  }

  public OrderDto makeOrderLinks(OrderDto order, Integer userId) {
    Link selfLink =
        linkTo(methodOn(UserController.class).getUserOrder(userId, order.getId())).withSelfRel();
    order.add(selfLink);
    makeCertificateLinks(Collections.singletonList(order.getCertificate()));
    return order;
  }

  public CollectionModel<CertificateDto> makeCertificateLinks(List<CertificateDto> certificates) {
    if (certificates.isEmpty()) {
      return CollectionModel.of(certificates);
    }
    Link selfLink = linkTo(CertificateController.class).withSelfRel();
    for (CertificateDto certificate : certificates) {
      Link link =
          linkTo(methodOn(CertificateController.class).getCertificate(certificate.getId()))
              .withSelfRel();
      certificate.setTags(new HashSet<>(makeTagLinks(certificate.getTags()).getContent()));
      certificate.add(link);
    }
    return CollectionModel.of(certificates, selfLink);
  }

  public CertificateDto makeCertificateLinks(CertificateDto certificate) {
    Link selfLink =
        linkTo(methodOn(CertificateController.class).getCertificate(certificate.getId()))
            .withSelfRel();
    certificate.add(selfLink);
    certificate.setTags(new HashSet<>(makeTagLinks(certificate.getTags()).getContent()));
    return certificate;
  }

  public CollectionModel<TagDto> makeTagLinks(Set<TagDto> tags) {
    if (tags.isEmpty()) {
      return CollectionModel.of(tags);
    }
    List<TagDto> listTags = new ArrayList<>(tags);
    Link selfLink = linkTo(TagController.class).withSelfRel();
    for (TagDto tag : listTags) {
      Link link = linkTo(methodOn(TagController.class).getTag(tag.getId())).withSelfRel();
      tag.add(link);
    }
    return CollectionModel.of(listTags, selfLink);
  }

  public TagDto makeTagLinks(TagDto tag) {
    Link selfLink = linkTo(methodOn(TagController.class).getTag(tag.getId())).withSelfRel();
    tag.add(selfLink);
    return tag;
  }
}
