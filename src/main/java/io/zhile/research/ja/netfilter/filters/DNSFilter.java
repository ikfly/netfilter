package io.zhile.research.ja.netfilter.filters;

import io.zhile.research.ja.netfilter.enums.RuleType;
import io.zhile.research.ja.netfilter.models.FilterRule;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class DNSFilter {
    public static final List<FilterRule> RULES;

    static {
        RULES = new ArrayList<>();      // TODO read from config file
        RULES.add(new FilterRule(RuleType.EQUAL, "zhile.io"));
    }

    public static String testQuery(String host) throws IOException {
        if (null == host) {
            return null;
        }

        for (FilterRule rule : RULES) {
            switch (rule.getType()) {   // TODO rewrite
                case EQUAL:
                    if (host.equals(rule.getContent())) {
                        System.out.println("=== reject dns query: " + host);
                        throw new java.net.UnknownHostException();
                    }
                default:    // TODO support more rule types
                    return host;
            }
        }

        return host;
    }

    public static Object testReachable(InetAddress n) throws IOException {
        if (null == n) {
            return null;
        }

        for (FilterRule rule : RULES) {
            switch (rule.getType()) {   // TODO rewrite
                case EQUAL:
                    if (n.getHostName().equals(rule.getContent())) {
                        System.out.println("=== reject dns reachable test: " + n.getHostName());
                        return false;
                    }
                default:    // TODO support more rule types
                    return null;
            }
        }

        return null;
    }
}