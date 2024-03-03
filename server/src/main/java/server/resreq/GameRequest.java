package server.resreq;

import dataAccess.objects.AuthToken;

public record GameRequest(String gameName, Integer gameID, String playerColor) {}
